package com.example.student.i_feigndto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeignEnrollmentDTO {
    private Long enrollmentId;
    private Long marks;
}
