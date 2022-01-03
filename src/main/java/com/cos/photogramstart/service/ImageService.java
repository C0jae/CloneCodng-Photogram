package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
    
    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public List<Image> imageStory(int principalId) {
        List<Image> images = imageRepository.mStory(principalId);
        
        return images;
    }

    @Value("${file.path}")
    private String uploadFolder;

    // DB에 변화를 주게된다면 꼭 @Transactional 해야한다.
    //  ㄴ 2개 이상의 DB변화가 있을 경우 중간에 문제가 생겨버려 해당지점까지만 변경되면 큰 문제기 때문(ex) 계좌송금 중 문제 발생)
    @Transactional
    public void imageUpload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID(); // uuid : 유일한 이름을 부여
        String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
        System.out.println("이미지 파일이름 : " + imageFileName);

        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        // 통신 or I/O가 일어날 때에는 예외가 발생할 수 있다.
        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // image 테이블에 저장
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
        imageRepository.save(image);
        // Image imageEntity = imageRepository.save(image);

        // 오류발생 : imageEntity에 user가 있고 user에는 image가 있기 때문에 계속 무한참조 발생
        // -> 해결방안 48강 확인
        // System.out.println(imageEntity);
    }
}
