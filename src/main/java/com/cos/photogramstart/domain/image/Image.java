package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity 
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String caption; // 코멘트느낌
    private String postImageUrl;    // 사진을 전송받아 그 사진을 서버 특정 폴더에 저장 - DB에는 저장된 경로를 insert

    @JsonIgnoreProperties({"images"})   // user.java에 정보를 불러올 때 images정보는 무시
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    // 이미지 좋아요 표시 컬럼 추가예정
    // 댓글

    private LocalDateTime createDate;

    @PrePersist // DB에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
