package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // 디비에 테이블 생성
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호증가 전략이 데이터베이스를 따라간다
    private int id;

    @Column(length = 20 ,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;
    private String website;         // 웹사이트
    private String bio;  
    
    @Column(nullable = false)       // 자기소개
    private String email;
    private String phone;
    private String gender;

    private String profileImgUrl;   // 사진
    private String role;            // 권한

    // 연관관계의 주인이 아니다 -> DB테이블에 컬럼을 만들지 않는다.
    // User를 Select할 때 해당 User에 해당하는 image들은 전부 가져온다.
    // Lazy => User를 Select할 때 해당 User에 해당하는 image들을 전부 가져오지 않는다. -> 대신 getImages() 함수의 image들이 호출될 때 가져온다.
    // Eager => User를 Select할 때 해당 User에 해당하는 image들을 전부 Join해서 가져온다.
    // @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"})
    private List<Image> images; // 양방향 매핑

    private LocalDateTime createDate;

    @PrePersist // DB에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User [id = " + id +", username=" + username + ", password = " + password + ", name = " + name + ", website = " 
            + website + ", bio = " + bio + ", email = " + email + ", phone = " + phone + ", gender = " + gender + 
            ", profileImgUrl = " + profileImgUrl + ", role = " + role + ", images = " + images + ", createDate = " + createDate + "]";
    }

}
