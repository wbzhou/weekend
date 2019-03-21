package com.littleWeekend.util;

/**
 * @Description:
 * @Author: junting
 * @CreateDate: 2019/3/20$ 13:44$
 * @Version: 1.0
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncriptMD5 {
//    public static void main(String[] args) {//主程序入口
//        MD5("15110262561GAG20181114LH");
//    }

    public static String MD5(String sourceStr) {
        String result = "";//通过result返回结果值
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");//1.初始化MessageDigest信息摘要对象,并指定为MD5不分大小写都可以
            md.update(sourceStr.getBytes());//2.传入需要计算的字符串更新摘要信息，传入的为字节数组byte[],将字符串转换为字节数组使用getBytes()方法完成
            byte b[] = md.digest();//3.计算信息摘要digest()方法,返回值为字节数组

            int i;//定义整型
            //声明StringBuffer对象
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];//将首个元素赋值给i
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");//前面补0
                buf.append(Integer.toHexString(i));//转换成16进制编码
            }
            result = buf.toString();//转换成字符串
            System.out.println("MD5(" + sourceStr + ",32) = " + result);//输出32位16进制字符串
            System.out.println("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24));//输出16位16进制字符串
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;//返回结果
    }
}
