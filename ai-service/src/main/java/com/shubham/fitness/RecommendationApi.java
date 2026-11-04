package com.shubham.fitness;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import java.time.Instant;
import java.util.*;
@Document("recommendations")
class Recommendation {
 @Id String id; String activityId; String userId; String advice; Instant createdAt=Instant.now();
 Recommendation(){} Recommendation(String a,String u,String t){activityId=a;userId=u;advice=t;}
 public String getId(){return id;} public String getActivityId(){return activityId;} public String getUserId(){return userId;} public String getAdvice(){return advice;} public Instant getCreatedAt(){return createdAt;}
}
interface RecommendationRepository extends MongoRepository<Recommendation,String>{Optional<Recommendation> findFirstByActivityId(String activityId);}
@Service
class GeminiClient {
 private final RestClient http; private final String key; private final String model;
 GeminiClient(RestClient.Builder builder,@Value("${GEMINI_API_KEY:}") String key,@Value("${GEMINI_MODEL:gemini-2.0-flash}") String model){this.http=builder.baseUrl("https://generativelanguage.googleapis.com").build();this.key=key;this.model=model;}
 String generate(String activity){
  if(key.isBlank())return "Demo guidance: hydrate, sleep adequately and increase training load gradually. Configure GEMINI_API_KEY for Gemini-generated guidance.";
  Map body=Map.of("contents",List.of(Map.of("parts",List.of(Map.of("text","Give safe, general fitness recovery guidance for this activity. Do not diagnose or prescribe: "+activity)))));
  Map result=http.post().uri("/v1beta/models/{model}:generateContent?key={key}",model,key).contentType(MediaType.APPLICATION_JSON).body(body).retrieve().body(Map.class);
  try{List<Map> candidates=(List<Map>)result.get("candidates");Map c=(Map)candidates.get(0).get("content");List<Map> parts=(List<Map>)c.get("parts");return String.valueOf(parts.get(0).get("text"));}catch(Exception e){throw new IllegalStateException("Unexpected Gemini response",e);}
 }
}
@Component
class ActivityConsumer {
 private final ObjectMapper mapper; private final GeminiClient gemini; private final RecommendationRepository repo;
 ActivityConsumer(ObjectMapper mapper,GeminiClient gemini,RecommendationRepository repo){this.mapper=mapper;this.gemini=gemini;this.repo=repo;}
 @KafkaListener(topics="fitness.activity-events",groupId="fitness-ai")
 void receive(String event)throws Exception{JsonNode n=mapper.readTree(event);String id=n.path("id").asText();if(id.isBlank())return;repo.save(new Recommendation(id,n.path("userId").asText(),gemini.generate(event)));}
}
@RestController @RequestMapping("/api/recommendations")
class RecommendationApi {
 private final RecommendationRepository repo; RecommendationApi(RecommendationRepository repo){this.repo=repo;}
 @GetMapping("/{activityId}") Recommendation get(@PathVariable String activityId){return repo.findFirstByActivityId(activityId).orElseThrow(()->new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND,"Recommendation not ready"));}
}