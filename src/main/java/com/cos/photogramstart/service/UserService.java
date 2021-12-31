package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationAPIException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(readOnly = true)
    public UserProfileDto userProfile(int pageUserId, int principalId) {
        UserProfileDto dto = new UserProfileDto();

        // SELECT * FROM image WHERE userId = :userId
        User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지 입니다.");
        });

        dto.setUser(userEntity);
        dto.setPageOwnerState(pageUserId == principalId); // true이면 페이지 주인, false이면 다른 이용자
        dto.setImageCount(userEntity.getImages().size());

        return dto;
    }

    @Transactional
    public User userUpdate(int id, User user) {
        // 1. 영속화
        // 람다식
        User userEntity = userRepository.findById(id).orElseThrow(() -> {
            return new CustomValidationAPIException("찾을 수 없는 ID 입니다.");}
            );

        // 2. 영속화된 오브젝트를 수정 - 더티체킹 (업데이트 완료)
        userEntity.setName(user.getName());

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setPassword(encPassword);
        userEntity.setWebsite(user.getWebsite());
        userEntity.setBio(user.getBio());
        userEntity.setGender(user.getGender());
        userEntity.setPhone(user.getPhone());
        
        return userEntity;
    }   // 더티체킹이 일어나서 업데이트 완료

}
