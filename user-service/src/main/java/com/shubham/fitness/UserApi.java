package com.shubham.fitness;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Entity @Table(name="fitness_users")
class UserProfile {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
 @NotBlank @Size(max=100) String name;
 @Email @NotBlank @Column(unique=true) String email;
 @Min(1) @Max(120) Integer age;
 public UserProfile(){}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public Integer getAge(){return age;} public void setAge(Integer v){age=v;}
}
interface UserRepository extends JpaRepository<UserProfile,Long>{}
@RestController @RequestMapping("/api/users")
class UserApi {
 private final UserRepository repo; UserApi(UserRepository repo){this.repo=repo;}
 @GetMapping List<UserProfile> all(){return repo.findAll();}
 @GetMapping("/{id}") UserProfile get(@PathVariable Long id){return repo.findById(id).orElseThrow();}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) UserProfile create(@Valid @RequestBody UserProfile p){return repo.save(p);}
}