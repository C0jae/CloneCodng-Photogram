package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service    // 1. ioC   2. 트랜잭션 관리
public class AuthService {

    private final UserRepository userRepository;

    public User signup(User user) {
        // .save가 저장 후 저장된 값을 리턴해주기 때문에 User변수에 저장 가능하다.
        User userEntity = userRepository.save(user);
        return userEntity;
    }

    
}
