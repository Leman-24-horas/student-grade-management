/* this works but uses some other dependencies which I've
 * removed from pom.xml for the time being
 * test runs using mvn test
 */

// package com.example.student.test_repository;

// import org.assertj.core.api.Assertions;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.test.context.junit4.SpringRunner;

// import com.example.student.a_entity.Student;
// import com.example.student.b_repository.StudentRepository;

// @RunWith(SpringRunner.class)
// @DataJpaTest
// public class StudentRepositoryTest {

//     @Autowired // for tests - field injection is preferred
//     private StudentRepository studentRepository;

//     // as a general rule of thumb - all tests should be of return-type void
//     @Test
//     public void studentRepository_Save_ReturnNewStudentAdded() {

//         // Arrange - Get jar of water
//         Long sid = 1L;
//         Long marks = 52L;
//         Student student = new Student(sid, "Ramu", marks);

//         // Act
//         Student addedStudent = studentRepository.save(student);

//         // Assert
//         Assertions.assertThat(addedStudent).isNotNull();
//         Assertions.assertThat(addedStudent.getId()).isGreaterThan(0);
//     }

//     @Test
//     public void testMethodRuns() {
//         System.out.println("Test is running!");
//     }

// }
