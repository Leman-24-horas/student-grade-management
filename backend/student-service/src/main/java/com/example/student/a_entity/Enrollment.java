package com.example.student.a_entity;

import lombok.*;
import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Enrollment")
@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eid", nullable = false, unique = true)
    private Long enrollmentId; 

    @ManyToOne
    @JoinColumn(name = "sid", referencedColumnName = "sid", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "cid", nullable = false)
    private Course course;

    @Column(name = "marks", nullable = false)
    private Long marks;

    public Enrollment(Student student, Course course, Long marks) {
        this.student = student;
        this.course = course;
        this.marks = marks;
    }

    

    
}
