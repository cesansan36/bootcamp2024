package com.pragma.bootcamp.adapters.driven.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user", url = "http://localhost:8080/users")
public interface UserFeignClient {

    @PostMapping("/validate/restricted")
    ResponseEntity<Boolean> validateRestricted(@RequestHeader("Authorization") String token);
}
