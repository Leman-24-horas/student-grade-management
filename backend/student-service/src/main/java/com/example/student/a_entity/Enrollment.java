package com.example.student.a_entity;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Enrollment")
@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "eid", nullable = false, unique = true)
    private Long enrollmentId; 

    @ManyToOne
    @JoinColumn(name = "sid", referencedColumnName = "sid", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "cid", nullable = false)
    private Course course;

    @Column(name = "letter_grade", nullable = false)
    /* Dependency added for this in pom.xml
     * If code breaks add another dependency given by chatgpt
     */
    @Pattern(regexp = "A|B|C|D|E|U", message = "Letter grade is between A to E and U if lower than E")
    private String grade;

    public Enrollment(Student student, Course course, String grade) {
        this.student = student;
        this.course = course;
        this.grade = grade;
    }

    

    
}
