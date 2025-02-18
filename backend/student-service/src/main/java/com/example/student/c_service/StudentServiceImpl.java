package com.example.student.c_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.b_repository.StudentRepository;
import com.example.student.a_entity.*;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;

    @Autowired // DI
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> listAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student findStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
        // TODO: StudentNotFoundException
    }

    @Override
    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long idOfCurrentStudent, Student newStudentInfo) {
        /*
         * Step 1 - Find the existing student using his/her id
         * Step 2 - Extract the new student details from the new Student entity
         * Step 3 - use getters and setters to set new values to the old values
         * 
         * Extra - TO DO
         * ------
         * (a) Need to handle null - create and implement an exception
         */

         Student currentStudent = studentRepository.findById(idOfCurrentStudent).orElse(null);
         currentStudent.setName(newStudentInfo.getName());
         currentStudent.setMarks(newStudentInfo.getMarks());
         studentRepository.save(currentStudent);

         return currentStudent;
    }

    @Override
    public void removeStudent(Long id) {
        studentRepository.deleteById(id);
    }

    
}
