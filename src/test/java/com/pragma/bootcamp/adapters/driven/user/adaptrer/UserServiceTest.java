package com.pragma.bootcamp.adapters.driven.user.adaptrer;

import com.pragma.bootcamp.adapters.driven.user.exception.UserNotValidException;
import com.pragma.bootcamp.adapters.driven.user.feign.UserFeignClient;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    UserService userService;

    UserFeignClient userFeignClient;

    @BeforeEach
    void setUp() {
        userFeignClient = mock(UserFeignClient.class);
        userService = new UserService(userFeignClient);
    }

    @Test
    void validateRestrictedSuccess() {
        ResponseEntity<Boolean> response = ResponseEntity.ok(true);
        when(userFeignClient.validateRestricted(anyString())).thenReturn(response);

        userService.validateRestricted("token");

        verify(userFeignClient, times(1)).validateRestricted(anyString());
    }

    @Test
    void validateRestrictedForbidden() {
        when(userFeignClient.validateRestricted(anyString())).thenThrow(FeignException.class);

        assertThrows(UserNotValidException.class, () -> userService.validateRestricted("token"));
    }
}
