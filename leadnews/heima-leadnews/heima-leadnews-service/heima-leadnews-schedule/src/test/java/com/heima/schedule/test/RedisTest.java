package com.heima.schedule.test;

import com.heima.common.redis.CacheService;
import com.heima.schedule.ScheduleApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@SpringBootTest(classes = ScheduleApplication.class)
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    private CacheService cacheService;
    @Test
    public void testList(){
        //リストの左側に要素を追加する
        cacheService.lLeftPush("list_001","hello,redis");
        //リストの右側に要素を追加する，消します
        String list_001 = cacheService.lRightPop("list_001");
        System.out.println(list_001);

    }
    @Test
    public void testZset(){
        //ソートされたセットにデータを追加する
//        cacheService.zAdd("zset_key_001","hello zset 001",1000);
//        cacheService.zAdd("zset_key_001","hello zset 002",8888);
//        cacheService.zAdd("zset_key_001","hello zset 003",7777);
//        cacheService.zAdd("zset_key_001","hello zset 004",999999);
        Set<String> zset_key_001 = cacheService.zRangeByScore("zset_key_001",0,8888);
        System.out.println(zset_key_001);

    }
}
