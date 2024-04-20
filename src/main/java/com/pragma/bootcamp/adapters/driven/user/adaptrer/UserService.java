package com.pragma.bootcamp.adapters.driven.user.adaptrer;

import com.pragma.bootcamp.adapters.driven.user.feign.UserFeignClient;
import com.pragma.bootcamp.domain.secondaryport.IUserValidationPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService implements IUserValidationPort {

    private final UserFeignClient userFeignClient;

    @Override
    public void validateRestricted(String token) {
        userFeignClient.validateRestricted(token);
    }
}
