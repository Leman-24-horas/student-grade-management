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

2. **Set up the database:** Open MySQL and create a new connection. Give it your desired name and ensure that its `port` is set to `3306`. Now in MySQL, create a database named ```student_management``` by running the following command:
    ```bash
    create schema student_management;
    use student_management;
    ```

3. **Configure Application Properties:** Modify the ```application.properties``` file in the following directories: 

    ```bash
    backend/grade-service/src/main/resources/application.properties
    backend/student-service/src/main/resources/application.properties
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

### **Student Service**
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

- `DELETE localhost:8080/student/delete/{id}`: Delete a specific student entity

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

- `DELETE localhost:8080/course/delete/{id}`: Delete a specific course entity

### **Enrollment Service**
- `GET localhost:8080/enrollment/all`: Get all enrollments
- `GET localhost:8080/enrollment/{id}`: Get a specific enrollment
- `GET localhost:8080/enrollment/course/{id}`: Get a specific enrollment by courseId

- `POST localhost:8080/enrollment/add`: Create an enrollment entity \
   Use the following JSON Body for the POST method   
   ```
    {
	    "studentId": anyValidStudentId,
        "courseId": anyValidCourseId,
        "marks": marks
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

- `DELETE localhost:8080/enrollment/delete/{id}`: Delete a specific enrollment entity

- `POST localhost:8080/enrollment/calculate-grade/{enrollmentId}`: Calculate letter grade for an enrollment entity
- `GET localhost:8080/enrollment/grade/get/{enrollmentId}`: Get letter grade for enrollment


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

## Demo
Please find the video for the demo of this SpringBoot Application [here](https://drive.google.com/file/d/1kIWpmmXKz9ZXOuxJbdEvUKCj-YYDDlIR/view?usp=sharing).

If there are any issues with playing the video on the cloud, please download the video and play it locally on your computer.

## Acknowledegements
This project is independently developed and is not derived from any specific online course or tutorial. All concepts and implementations were assembled through original work, guided only by publicly available resources. A list of reference materials used during development is provided below.

- Integrating MySQL with SpringBoot: 
    - https://youtu.be/fgVoVt2EGpM
    - https://spring.io/guides/gs/accessing-data-mysql
- JPA Associations for linking Student and Course entities with Enrollment entity: 
    - https://www.baeldung.com/jpa-hibernate-associations
- Rest Controllers
    - https://stackoverflow.com/questions/33711986/spring-boot-how-to-set-a-common-path-for-multiple-restcontrollers
- Response Entities
    - https://www.baeldung.com/spring-response-entity
    - https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html
- Unit Testing in SpringBoot: 
    - https://medium.com/@AlexanderObregon/mastering-spring-boot-testing-with-junit-and-mockito-8bec9b4911fc
    - https://youtu.be/jqwZthuBmZY
- Implementing Data Loader in SpringBoot
    - https://www.geeksforgeeks.org/spring-boot-load-initial-data/?ref=gcse_outind
- Understanding Feign Client: 
    - https://www.baeldung.com/spring-boot-feignclient-vs-webclient
    - https://youtu.be/DACsOJomLI0
    - https://rameshfadatare.medium.com/how-to-create-and-deploy-spring-boot-microservices-using-docker-fa1757d0805a
- Creating a Global Exception Handler for grade-service
    - https://www.geeksforgeeks.org/spring-webflux-rest-api-global-exception-handling/?ref=header_outind
- Resources used for debugging
    - https://stackoverflow.com/questions/73288515/jpa-and-deprecated-getone-and-getbyid-how-to-catch-entitynotfoundexceptio
    - https://stackoverflow.com/questions/75596496/cannot-deserialize-value-of-type-java-lang-long-from-object-value-token-json
    - https://stackoverflow.com/questions/9186604/mockito-exception-when-requires-an-argument-which-has-to-be-a-method-call-on

<br>

**Note:** A significant portion of the foundational knowledge and self-directed learning for this project was also informed by the materials provided in *CS203: Collaborative Software Development*.

