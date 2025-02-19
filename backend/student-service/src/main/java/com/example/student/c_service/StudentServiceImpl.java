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
        if (studentFromDB == null) throw new StudentNotFoundException(id);
        return studentFromDB;
    }

    // @Override
    // public void addStudent(Student student) {
    //     String name = student.getName();
    //     List<Student> allStudents = listAllStudents();
        
    //     for(Student existingStudent : allStudents) {
    //         String existingName = existingStudent.getName();

    //         if(existingName.equals(name)) {
    //             throw new StudentAlreadyExistsException(existingName);
    //         }
    //     }

    //     studentRepository.save(student);
    // }

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
        /*
         * Step 1 - Find the existing student using his/her id
         * Step 2 - Extract the new student details from the new Student entity
         * Step 3 - use getters and setters to set new values to the old values
         * 
         * Extra - TO DO
         * ------
         * (a) Need to handle null - create and implement an exception
         * (b) Only marks can be updated not name
         */

         Student currentStudent = findStudentById(idOfCurrentStudent);
        //  if (!currentStudent.getName().equals(newStudentInfo.getName())) {
        //     throw new NameCannotBeUpdatedException();
        // }
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
