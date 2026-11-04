package com.shubham.fitness;
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
 public String getId(){return id;} public String getUserId(){return userId;} public String getType(){return type;} public double getDurationMinutes(){return durationMinutes;} public double getCaloriesBurned(){return caloriesBurned;} public Instant getCreatedAt(){return createdAt;}
}
interface ActivityRepository extends MongoRepository<Activity,String>{List<Activity> findByUserIdOrderByCreatedAtDesc(String userId);}
@RestController @RequestMapping("/api/activities")
class ActivityApi {
 private final ActivityRepository repo; private final KafkaTemplate<String,Activity> kafka;
 ActivityApi(ActivityRepository repo,KafkaTemplate<String,Activity> kafka){this.repo=repo;this.kafka=kafka;}
 @GetMapping List<Activity> all(@RequestParam(required=false) String userId){return userId==null?repo.findAll():repo.findByUserIdOrderByCreatedAtDesc(userId);}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) Activity create(@Valid @RequestBody Activity a){Activity saved=repo.save(a);kafka.send("fitness.activity-events",saved.id,saved);return saved;}
}