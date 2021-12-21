package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service    // 1. ioC   2. 트랜잭션 관리
public class AuthService {

    // user 테이블 JapRepository 인스턴스
    private final UserRepository userRepository;

    // 비밀번호 해시
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional  // Write(Insert, Update, Delete)
    public User signup(User user) {
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("ROLE_USER");  // 관리자 = ROLE_ADMIN

        // .save가 저장 후 저장된 값을 리턴해주기 때문에 User변수에 저장 가능하다.
        User userEntity = userRepository.save(user);
        return userEntity;
    }

    
}
