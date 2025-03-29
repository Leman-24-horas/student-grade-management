package com.example.student.i_feigndto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Grade {
    private Long gradeId;
    private Long enrollmentId;
    private Long marks;
    private String letterGrade;
}
