package com.example.student.c_service;

import java.util.List;

import com.example.student.a_entity.Student;

public interface StudentService {
    List<Student> listAllStudents();
    Student findStudentById(Long studentId);
    Student addStudent(Student student);
    Student updateStudent(Long idOfExistingStudent, Student newStudent);
    void removeStudent(Long studentId);
    void deleteAllStudents();
}
