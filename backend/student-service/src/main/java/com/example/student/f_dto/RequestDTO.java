package com.example.student.f_dto;

import lombok.Getter;
import lombok.Setter;

public class RequestDTO {
    
    @Getter
    @Setter
    public static class CreateEnrollment {
        Long studentId;
        Long courseId;
        String grade;
    }
}

/* also possible
 * public class CreateClanUserDTO {
 *  private Long xxx;
 * }
 */
