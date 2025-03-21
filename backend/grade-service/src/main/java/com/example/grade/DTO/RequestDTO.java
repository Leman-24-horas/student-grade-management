package com.example.grade.DTO;

import lombok.Getter;
import lombok.Setter;

public class RequestDTO {

    @Getter
    @Setter
    public static class Enrollment {
        private Long enrollmentId;
        private Long marks;
    }

    @Getter
    @Setter
    public static class Marks {
        private Long marks;
    }
    
}
