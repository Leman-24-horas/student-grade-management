package com.example.student.a_entity;

import java.util.Objects;

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
@Table(name = "Student"/*
                        * , schema = "StudentDBSpringGitHub" - Doesn't work because in
                        * application.properties the code is connected to a pre-specified schema
                        */)
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sid")
    private Long id;

    @Column(name = "sname", nullable = false, unique = true)
    // unique = true only applies when creating fresh schema
    // if schema already exists then unique = true doesn't work
    private String name;

    @Column(name = "marks", nullable = false)
    private Long marks;

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
        return id == student.id && name == student.name && marks == student.marks;
    }

    // Same reason as above for equals - again not needed, just good practice
    @Override
    public int hashCode() {
        return Objects.hash(id, name, marks);
    }

}
