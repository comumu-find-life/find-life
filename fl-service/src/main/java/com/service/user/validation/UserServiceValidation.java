package com.service.user.validation;

public interface UserServiceValidation {
    void validateSignUp(String email, String nickName) throws Exception;
}
