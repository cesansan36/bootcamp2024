package com.pragma.bootcamp.adapters.driven.user.adaptrer;

import com.pragma.bootcamp.adapters.driven.user.exception.UserNotValidException;
import com.pragma.bootcamp.adapters.driven.user.feign.UserFeignClient;
import com.pragma.bootcamp.adapters.driven.user.util.FeignConstants;
import com.pragma.bootcamp.domain.secondaryport.IUserValidationPort;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService implements IUserValidationPort {

    private final UserFeignClient userFeignClient;

    @Override
    public void validateRestricted(String token) {
        try {
            userFeignClient.validateRestricted(token);
        }
        catch (FeignException e) {
            throw new UserNotValidException(FeignConstants.FORBIDDEN_STATUS_RECEIVED_MESSAGE);
        }
    }
}
