package com.example.demo.facility;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class Facility {
    @Id
    @SequenceGenerator(
            name = "facility_sequence",
            sequenceName = "student_sequence",
            allocationSize =1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "facility_sequence"

    )
    private Long id;
    private Long ownerId; //user that owns the facility
    private String name;
    private String location;

    public Facility(){


    }
    public Facility(Long ownerId, String name, String location) {
        this.ownerId = ownerId;
        this.name = name;
        this.location = location;
    }

    public Facility(Long id, Long ownerId, String name, String location) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.location = location;
    }

    public Facility( String name, String location) {
        this.name = name;
        this.location = location;
    }
}
