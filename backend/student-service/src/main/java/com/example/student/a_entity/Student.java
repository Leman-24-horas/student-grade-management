package com.example.student.a_entity;

import java.util.List;
import java.util.Objects;

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
@Table(name = "Student"/*
                        * , schema = "StudentDBSpringGitHub" - Doesn't work because in
                        * application.properties the code is connected to a pre-specified schema
                        */)
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid")
    private Long studentId;

    @Column(name = "sname", nullable = false, unique = true)
    // unique = true only applies when creating fresh schema
    // if schema already exists then unique = true doesn't work
    private String studentName;

    // @Column(name = "marks", nullable = false)
    // private Long marks;

    /* equals() method overriden 
     * Not needed but did for unit testing so that assertEquals() works but assert still works even without overriding equals()
     * if you have 2 lists containing the same set of students in the same order assertEqual will show them as false 
     * because they take up a different part of the memeory
     * So, overriding here so that the actual attributes of Student objects are compared instead of their memory locations
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Student student = (Student) obj;
        return studentId == student.studentId && studentName == student.studentName;
    }

    // Same reason as above for equals - again not needed, just good practice
    @Override
    public int hashCode() {
        return Objects.hash(studentId, studentName);
    }

    /* mappedBy = exact name of the variable in Enrollment */
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Enrollment> enrollments;

    /* Enrollment has been added hence constructor needs to be manually defined */
    public Student(Long studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }
}
