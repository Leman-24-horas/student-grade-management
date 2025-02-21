package com.example.student.c_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.student.b_repository.StudentRepository;
import com.example.student.e_exceptions.*;
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
        Student studentFromDB = studentRepository.findById(id).orElse(null);
        if (studentFromDB == null)
            throw new StudentNotFoundException(id);
        return studentFromDB;
    }

    // After using unique = true in student
    @Override
    public void addStudent(Student student) {
        try {
            studentRepository.save(student);
        } catch (DataIntegrityViolationException e) {
            if (student.getName() == null) {
                throw new IllegalArgumentException("Name cannot be null");
            } else {
                throw new StudentAlreadyExistsException(student.getName());
            }
        }
    }

    @Override
    public Student updateStudent(Long idOfCurrentStudent, Long marks) {
        Student currentStudent = findStudentById(idOfCurrentStudent);
        currentStudent.setMarks(marks);
        studentRepository.save(currentStudent);

        return currentStudent;
    }

    @Override
    public void removeStudent(Long id) {
        Student studentToBeRemoved = findStudentById(id);
        studentRepository.delete(studentToBeRemoved);
    }
}
