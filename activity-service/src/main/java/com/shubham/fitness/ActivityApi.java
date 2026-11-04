package com.shubham.fitness;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;
@Document("activities")
class Activity {
 @Id String id; @NotBlank String userId; @NotBlank String type; @Positive double durationMinutes; @PositiveOrZero double caloriesBurned; Instant createdAt=Instant.now();
 public String getId(){return id;} public String getUserId(){return userId;} public void setUserId(String v){userId=v;} public String getType(){return type;} public void setType(String v){type=v;} public double getDurationMinutes(){return durationMinutes;} public void setDurationMinutes(double v){durationMinutes=v;} public double getCaloriesBurned(){return caloriesBurned;} public void setCaloriesBurned(double v){caloriesBurned=v;} public Instant getCreatedAt(){return createdAt;}
}
interface ActivityRepository extends MongoRepository<Activity,String>{List<Activity> findByUserIdOrderByCreatedAtDesc(String userId);}
@RestController @RequestMapping("/api/activities")
class ActivityApi {
 private final ActivityRepository repo; private final KafkaTemplate<String,String> kafka; private final ObjectMapper mapper;
 ActivityApi(ActivityRepository repo,KafkaTemplate<String,String> kafka,ObjectMapper mapper){this.repo=repo;this.kafka=kafka;this.mapper=mapper;}
 @GetMapping List<Activity> all(@RequestParam(required=false) String userId){return userId==null?repo.findAll():repo.findByUserIdOrderByCreatedAtDesc(userId);}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) Activity create(@Valid @RequestBody Activity a) throws JsonProcessingException {Activity saved=repo.save(a);kafka.send("fitness.activity-events",saved.id,mapper.writeValueAsString(saved));return saved;}
}