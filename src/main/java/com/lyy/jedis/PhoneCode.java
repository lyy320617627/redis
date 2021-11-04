package com.lyy.jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @program: jedis_redisdemo
 * @description: 手机验证码
 * @author: ly
 * @create: 2021-11-04 15:54
 **/

public class PhoneCode {
    public static void main(String[] args) {
//        System.out.println(getCode());
        //模拟验证码的发送
        verifyCode("1776725356");
        getRedisCode("17767253656","705005");
    }

    //1.生成6位的数字验证码
    public static String getCode(){
        Random random=new Random();
        String code="";
        for (int i=0;i<6;i++){
            int rand = random.nextInt(10);
            code+=rand;
        }
        return code;
    }
    //2.让每个手机每天只能发送三次，验证码放到redis中，设置过期时间
    public static void verifyCode(String phone){
        //连接redis
        Jedis jedis=new Jedis("192.168.139.128",6379);
        //拼接key
        //手机发送次数key
        String countKey="VerifyCode"+phone+":count";
        //验证码
        String codeKey="Verify"+phone+":code";
        //每个手机每天只能发送三次
        String count = jedis.get(countKey);
      if (count==null){
          //没有发送次数，第一次发送
          //设置发送次数是1
          jedis.setex(countKey,24*60*60,"1");
      }else if (Integer.parseInt(count)<=2){
          //发送次数加1
          jedis.incr(countKey);
      } else if (Integer.parseInt(count)>2){
          //发送三次，不能再进行发送
          System.out.println("今天发送次数已经超过三次");
          jedis.close();
      }
      //发送验证码要放入到redis里面去
        String vcode = getCode();
      jedis.setex(codeKey,120,vcode);
      jedis.close();
    }
    //3验证码的校验
    public static void getRedisCode(String phone,String code){
        //首先，从redis中获取验证码
        Jedis jedis=new Jedis("192.168.139.128",6379);
        String codeKey="VerifyCode"+phone+":code";
        String redis=jedis.get(codeKey);
        if (redis.equals(code)){
            System.out.println("成功");
        }else{
            System.out.println("失败");
        }
        jedis.close();
    }
}
