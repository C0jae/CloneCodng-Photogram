package com.cos.photogramstart.service;

import javax.transaction.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomAPIException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    public final SubscribeRepository subscribeRepository;
    
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
