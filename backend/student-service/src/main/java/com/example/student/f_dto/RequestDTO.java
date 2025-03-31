package com.example.student.f_dto;

import lombok.Getter;
import lombok.Setter;

public class RequestDTO {
    
    @Getter
    @Setter
    public static class CreateEnrollment {
        Long studentId;
        Long courseId;
        Long marks;
    }

    // For feign client
    // @Getter
    // @Setter
    // public static class Enrollment {
    //     private Long enrollmentId;
    //     private Long marks;
    // }

    @Getter
    @Setter
    public static class Marks {
        private Long marks;
    }

    // @Getter
    // @Setter
    // public static class EnrollmentWithGrade {
    //     private Long enrollmentId;
    //     private Long marks;
    //     private String grade;
    // }
}

/* also possible
 * public class CreateClanUserDTO {
 *  private Long xxx;
 * }
 */
