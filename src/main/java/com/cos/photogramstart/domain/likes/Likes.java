package com.cos.photogramstart.domain.likes;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity 
@Table(
    // 중복불가 unique키 설정(같은 값이 들어올 수 없다. => 중복하여 좋아요를 누를 수 없다.)
    uniqueConstraints = {
        @UniqueConstraint(
            name = "likes_uk",
            columnNames = {"imageId", "userId"}
        )
    }
)

public class Likes {    // N
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "imageId")   // 컬럼 이름 설정(imageId)
    @ManyToOne
    private Image image;    // 1

    // 오류발생(나중에 수정예정 => user를 불러올때 User클래스 안에 있는 Image 클래스도 불러오게 되어있기 때문 )
    @JoinColumn(name = "userId")    // 컬럼 이름 설정(userId)
    @ManyToOne
    private User user;  // 1

    private LocalDateTime createDate;

    @PrePersist // DB에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
