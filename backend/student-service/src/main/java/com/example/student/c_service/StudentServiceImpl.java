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
    public Student findStudentById(Long studentId) {
        Student studentFromDB = studentRepository.findById(studentId).orElse(null);
        if (studentFromDB == null)
            throw new StudentNotFoundException(studentId);
        return studentFromDB;
    }

    @Override
    public Student addStudent(Student student) {
        try {
            return studentRepository.save(student); // now this returns the added student instead of void
        } catch (DataIntegrityViolationException e) {
            if (student.getStudentName() == null) {
                throw new IllegalArgumentException("Name cannot be null");
            } else {
                throw new StudentAlreadyExistsException(student.getStudentName());
            }
        }
    }

    @Override
    public Student updateStudent(Long idOfExistingStudent, Student newStudent) {
        Student existingStudent = findStudentById(idOfExistingStudent);
        existingStudent.setStudentName(newStudent.getStudentName());
        studentRepository.save(existingStudent);

        return existingStudent;
    }

    @Override
    public void removeStudent(Long studentId) {
        Student studentToBeRemoved = findStudentById(studentId);
        studentRepository.delete(studentToBeRemoved);
    }
}
