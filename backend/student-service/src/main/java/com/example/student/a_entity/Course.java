package com.example.student.a_entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "course")
@Entity
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cid")
    private Long courseId;

    @Column(name = "cname", nullable = false, unique = true)
    private String courseName;

    /* orphanRemoval = true because if there is no course then there an be no enrollment */
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // no need to show enrollment when fetching course
    private List<Enrollment> enrollments;

    public Course(Long courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

}
