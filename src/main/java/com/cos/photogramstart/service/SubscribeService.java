package com.cos.photogramstart.service;

import javax.transaction.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    public final SubscribeRepository subscribeRepository;
    
    @Transactional
    public void subscribe(int fromUserId, int toUserId) {
        subscribeRepository.mSubscribe(fromUserId, toUserId);
    }

    @Transactional
    public void unSubscribe(int fromUserId, int toUserId) {
        subscribeRepository.mUnSubscribe(fromUserId, toUserId);
    }

}
