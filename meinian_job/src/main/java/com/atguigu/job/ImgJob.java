package com.atguigu.job;

import com.atguigu.constant.RedisConstant;
import com.atguigu.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg(){
        Set<String> set=jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,
                RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        for (String s : set) {
            System.out.println("删除图片的名称是:"+s);
            QiniuUtils.deleteFileFromQiniu(s);
        }
        jedisPool.getResource().del(RedisConstant.SETMEAL_PIC_RESOURCES);
        jedisPool.getResource().del(RedisConstant.SETMEAL_PIC_DB_RESOURCES);

    }

}
