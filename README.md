# Student Grade Management System
*created by Aditya Kumar Bhardwaj*

## Overview
**Student Grade Management System** is a backend application designed to manage and organize student-course information efficiently. It handles student and course details, enrollments, and letter grades for each enrollment. Built with **Java Spring Boot 3** using a **microservices architecture**, this system is scalable, and well-structured for future enhancements.

## Key Features
- **Student and Course Management:** Create, update, and delete student and course details.
- **Enrollment Tracking:** Maintain detailed records of student enrollments, including course associations and earned marks.
- **Letter Grade Calculation:** Convert numeric marks into letter grades based on predefined grading criteria.

## Technologies Used
- **Java:** Primary programming language for backend.
- **Spring Boot 3:** Framework for building this microservice-based architecture.
- **Spring Data JPA:** For object-relational mapping (ORM) and database interaction.
- **MySQL:** Main relational database for storing persistent data.
- **Spring Cloud:** For enabling interservice communication between microservices.
- **Mockito:** For unit testing.

## Microservices
1. **Student Service:** Manages student related details such as name and unique student id.
2. **Course Service:** Handles all course related details such as course name and course id. 
3. **Enrollment Service:** Manages student-course enrollments, maintaining records of which students are registered for which courses. It also stores marks for each student’s enrollment. 
4. **Grade Service:** Uses pre-defined grading criteria to compute letter grades from the marks stored in Enrollment entities. 

**Diagram 1** below explains how the microservices interact with each other:
![Image](diagrams/Microservice%20Architecture%20Diagram%20copy.jpg) 

**Diagram 2** below explains the architecture inside each individual microservice:
![Image](diagrams/Inside%20a%20Microservice%20Diagram.jpg)



## Prerequisites
- **Java 17** or higher
- **Maven** 3.x
- **MySQL** for database
- **Postman** for testing API endpoints

## Running the Application
1. **Clone the repository:**
    ```bash
    git clone https://github.com/Leman-24-horas/student-grade-management.git
    ```

2. **Set up the database:** Create a MySQL database named            ```student_management```.

3. **Configure Application Properties:** Modify the ```application. properties``` file in the following directories: 

    ```bash
    backend/grade-service/src/resources/application.properties
    backend/student-service/src/resources/application.properties
    ```
    Configure your database connection as follows:
    ```bash
    spring.datasource.url=jdbc:mysql://localhost:3306/student_management
    spring.datasource.username=your_db_username
    spring.datasource.password=your_db_password
    spring.jpa.hibernate.ddl-auto=update
    ```

4. **Build the Microservices:** Head over to the following directories 
    ```bash
    backend/grade-service
    backend/student-service
    ```

    and run the following Maven command
    ``` bash
    mvn clean install
    ```

5. **Run the Microservices:** After step 4, in the same directories run the following command:
    ``` bash
    .\mvnw spring-boot:run
    ```

6. **Access the APIs:** The APIs will be available at \
    ```http://localhost:8080``` for ```student-service``` and at \
    ```http://localhost:8081``` for ```grade-service```

## API Endpoints
Open your preferred API client (e.g., Postman) and access the following APIs

### **User Service**
- `GET localhost:8080/student/all`: Get all students
- `GET localhost:8080/student/{id}`: Get a specific student

- `POST localhost:8080/student/add`: Create a student entity \
   Use the following JSON Body for the POST method   
   ```
    {
	    "studentName": "Bob Dylan"
    }
   ```    

- `PUT localhost:8080/student/update/{id}`: Update details of a specific student \
    Use the following JSON Body for the PUT method   
   ```
    {
	    "studentName": "New_Student_Name"
    }

- `DELETE localhost:8080/student/delete/{id}`: Delete a specific student entry

### **Course Service**
- `GET localhost:8080/course/all`: Get all courses
- `GET localhost:8080/course/{id}`: Get a specific course

- `POST localhost:8080/course/add`: Create a course entity \
   Use the following JSON Body for the POST method   
   ```
    {
	    "courseName": "Software Development"
    }
   ```    

- `PUT localhost:8080/course/update/{id}`: Update details of a specific course \
    Use the following JSON Body for the PUT method   
   ```
    {
	    "courseName": "New_Course_Name"
    }

- `DELETE localhost:8080/course/delete/{id}`: Delete a specific course entry

### **Enrollment Service**
- `GET localhost:8080/enrollment/all`: Get all enrollments
- `GET localhost:8080/enrollment/{id}`: Get a specific enrollment
- `GET localhost:8080/enrollment/course/{id}`: Get a specific enrollment by courseId

- `POST localhost:8080/enrollment/add`: Create a enrollment entity \
   Use the following JSON Body for the POST method   
   ```
    {
	    "studentId": 1,
        "courseId": 1,
        "marks": 99
    }
   ```    

- `PUT localhost:8080/enrollment/update/{id}`: Update details of a specific enrollment \
    Use the following JSON Body for the PUT method   
   ```
    {
	    "studentId": newStudentId,
        "courseId": newCourseId,
        "marks": newMarks
    }

- `DELETE localhost:8080/enrollment/delete/{id}`: Delete a specific enrollment entry

- `POST localhost:8080/enrollment/calculate-grade/{enrollmentId}`: Calculate letter grade for an enrollment entity
- `GET localhost:8080/enrollment/grade/get/{id}  `: Get letter grade for enrollment


### **Grade Service**
- `DELETE localhost:8081/grade/delete/{enrollmentId}`: Delete a specific grade entity

## Running Tests

To run the unit tests use the following command in the directories of student and grade service:
```bash
mvn test
```

### Example Unit Test with Mockito

```java
@Test
void getStudent_InvalidId_ReturnNotFound() {
    when(studentService.findStudentById(2L)).thenThrow(StudentNotFoundException(2L));
        
    ResponseEntity<?> response = studentController.getStudent(2L);
        
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
    assertTrue(response.getBody() instanceof String);
    assertEquals("Could not find Student with Id = 2", response.getBody());
        
    verify(studentService, times(1)).findStudentById(2L);
}
```

## Future Improvements
- Containerize the application using **Docker** to allow for consistent code deployment across various environments and resolve the "it works on my machine" problem. 
- Expand the application by integrating additional microservices like
    - `performance`: a service separate from enrollment that stores marks by assessments taken by a student enrolled in a particular course. 
    - `assessment`: stores details such as type of assessment (quiz, project, finals etc), and assessment weightage allowing for a more comprehensive calculation of students' marks. 
    - `gateway-service`: for better managing inter-service communication and routing requests to the appropriate microservices. 
    - `authentication-service`: to facilitate user verification and authorization
- Develop a user interface. 
