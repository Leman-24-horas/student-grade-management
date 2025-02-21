package com.example.student.c_service;

import java.util.List;

import com.example.student.a_entity.Student;

public interface StudentService {
    public abstract List<Student> listAllStudents(); // public abstract is still there even if you don't write it
    Student findStudentById(Long id);
    void addStudent(Student student);
    Student updateStudent(Long idOfCurrentStudent, Long marks);
    void removeStudent(Long id);
}
