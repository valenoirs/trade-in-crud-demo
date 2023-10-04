package com.example.demo.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ValidationService {

    final private Validator validator;

    ValidationService(Validator validator){
        this.validator = validator;
    }

    public void validate(Object request){
        Set<ConstraintViolation<Object>> violation = validator.validate(request);

        if(!violation.isEmpty()){
            throw new ConstraintViolationException(violation);
        }
    }
}

