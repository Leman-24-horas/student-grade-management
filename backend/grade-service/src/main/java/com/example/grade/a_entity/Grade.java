package com.example.grade.a_entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "grade")
@Entity
public class Grade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gid", nullable = false, unique = true)
    private Long gradeId;

    @Column(name = "eid", nullable = false, unique = true)
    private Long enrollmentId;

    @Column(name = "marks", nullable = false)
    private Long marks;

    @Column(name = "grade", nullable = false)
    private String letterGrade;

    /* Constructor without grade because grade is calculated from marks */
    public Grade(Long gradeId, Long enrollmentId, Long marks) {
        this.gradeId = gradeId;
        this.enrollmentId = enrollmentId;
        this.marks = marks;
    }

    // Another constructor to be used in the add method in service
    public Grade(Long enrollmentId, Long marks, String letterGrade) {
        this.enrollmentId = enrollmentId;
        this.marks = marks;
        this.letterGrade = letterGrade;
    }

}

/* student in this enrollment has this letter grade */

