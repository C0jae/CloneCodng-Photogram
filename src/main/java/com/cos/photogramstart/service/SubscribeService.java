package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomAPIException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em; // Repository는 EntityManager를 구현해서 만들어져 있는 구현체이다.

    @Transactional(readOnly = true)
    public List<SubscribeDto> subscribeList(int principalId, int pageUserId) {
        
        // 쿼리 준비
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profileImgUrl, ");
        sb.append("if ((SELECT 1 FROM subscribe WHERE fromUserId = ? and toUserId = u.id), 1, 0) subscribeState, ");
        sb.append("if ((? = u.id), 1, 0) equalUserState ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ?");    // 마지막에 세미콜론(;) 절대 첨부하지 말것

        // 1. 물음표 : principalId
        // 2. 물음표 : principalId
        // 3. 물음표 : pageUserId

        // 쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
            .setParameter(1, principalId)
            .setParameter(2, principalId)
            .setParameter(3, pageUserId);

        // 쿼리 실행
        // qlrm 라이브러리 추가(DTO에 DB결과를 쉽게 매핑하기 위해)
        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);

        return subscribeDtos;
    }
    
    @Transactional
    public void subscribe(int fromUserId, int toUserId) {
        try {
            subscribeRepository.mSubscribe(fromUserId, toUserId);
        } catch (Exception e) {
            throw new CustomAPIException("이미 구독을 하였습니다.");
        }
    }

    @Transactional
    public void unSubscribe(int fromUserId, int toUserId) {
        subscribeRepository.mUnSubscribe(fromUserId, toUserId);
    }

}
