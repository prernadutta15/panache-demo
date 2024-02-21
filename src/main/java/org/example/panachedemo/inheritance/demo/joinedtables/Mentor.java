package org.example.panachedemo.inheritance.demo.joinedtables;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "jt_mentor")
@PrimaryKeyJoinColumn(name = "user_id")
public class Mentor extends User {

    private double averageRating;
    private Long id;

//    void f()
//    {
//       new User().getId();
//       new Mentor().getId();
//    }
}