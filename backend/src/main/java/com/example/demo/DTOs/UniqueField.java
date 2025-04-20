package com.example.demo.DTOs;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueFieldValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueField {
    String message() default "Giá trị đã tồn tại!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    // Thêm tên entity và tên field
    Class<?> entity();
    String fieldName();
}
