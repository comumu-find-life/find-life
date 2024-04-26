package com.service.user.validation;

import com.core.user.model.User;
import com.core.user.model.UserPoint;

public interface UserServiceValidation {
    void validateSignUp(String email, String nickName) throws Exception;

    void validateDecreasePoint(UserPoint userPoint, double price) throws  Exception;
}
