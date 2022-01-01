package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {
    
    @Modifying  // INSERT, DELETE, UPDATE 를 네이티브 쿼리로 작성하기 위해서는 해당 어노테이션이 필요
    @Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
    void mSubscribe(int fromUserId, int toUserId);    // void가 아닌 int일 경우 1(변경된 행의 갯수가 리턴), -1(오류), 0(해당되는 행이 없음)

    @Modifying
    @Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
    void mUnSubscribe(int fromUserId, int toUserId);  // 1, -1, 0

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :principalId and toUserId = :pageUserId", nativeQuery = true)
    int mSubscribeState(int principalId, int pageUserId);

    @Query(value = "SELECT COUNT(*) from subscribe where fromUserId = :pageUserId", nativeQuery = true)
    int mSubscribeCount(int pageUserId);

}
