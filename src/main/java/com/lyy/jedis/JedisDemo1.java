package com.lyy.jedis;

import com.sun.tracing.dtrace.ArgsAttributes;
import org.junit.Test;
import org.omg.CORBA.ARG_OUT;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * @program: jedis_redisdemo
 * @description:
 * @author: ly
 * @create: 2021-11-04 11:28
 **/

public class JedisDemo1 {
    public static void main(String[] args) {
        //创建有个jedis对象
        Jedis jedis=new Jedis("192.168.139.128",6379);
        //测试
         String ping = jedis.ping();
        System.out.println(ping);

    }
    @Test
    public void demo1(){
       Jedis jedis=new Jedis("192.168.139.128",6379);
        //添加数据
        jedis.set("name","lucy");
        String name = jedis.get("name");
        System.out.println(name);
        //设置多个ley-value
        jedis.mset("name","jack","k2","v2");
        List<String> mget = jedis.mget("k1", "k2");
        System.out.println(mget);
        Set<String> keys = jedis.keys("*");
        for (String key:keys){
            System.out.println(key);
        }
    }
    //操作list集合
    @Test
    public void demo2(){
        Jedis jedis=new Jedis("192.168.139.128",6379);
        jedis.lpush("key1","lucy","mary","jack");
        List<String> lrange = jedis.lrange("key1", 0, -1);
        System.out.println(lrange);
    }
    //操作set
    @Test
    public void demo3(){
        Jedis jedis=new Jedis("192.168.139.128",6379);
        jedis.sadd("name1","lucy");
        Set<String> name = jedis.smembers("name1");
        System.out.println(name);
    }
    //操作hash
    @Test
    public void demo4(){
        Jedis jedis=new Jedis("192.168.139.128",6379);
        jedis.hset("users","ages","20");
        String hget = jedis.hget("users", "ages");
        System.out.println(hget);
    }
    //操作zset
    @Test
    public void demo5(){
        Jedis jedis=new Jedis("192.168.139.128",6379);
        jedis.zadd("china",100d,"shanghai");
        Set<String> china = jedis.zrange("china", 0, -1);
        System.out.println(china);
    }
}
