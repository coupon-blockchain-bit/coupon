package com.bit.bonusPointsExchange.utils;

import java.text.SimpleDateFormat; 
import java.util.Date;  
import java.util.Random;
/**
 * 根据IP、时间和三位随机数生成文件前缀，防止文件名重复
 */
public class IpTimeStamp {
	private SimpleDateFormat sdf = null;//格式化时间操作类  
    private String ip = null;//定义一个ip地址  
    public IpTimeStamp(){}//JavaBean必须有无参构造  
    public IpTimeStamp(String ip){  
        this.ip = ip;  
    }  
    public String getIPTimeRand(){  
        StringBuffer buf = new StringBuffer();  
        if(this.ip!=null){//ip地址不为空，则以.进行拆分  
            String str[] = this.ip.split("\\.");  
            for(int i=0;i<str.length;i++){  
                buf.append(this.addZero(str[i],3));  
            }  
        }  
        buf.append(this.getTimeStamp());//没有IP地址，则直接追加时间戳  
        Random r = new Random();  
        for(int x=0;x<3;x++){//循环取得三个不大于10的随机整数  
            buf.append(r.nextInt(10));//  
        }  
        return buf.toString();  
    }  
    public String getDate(){//取得格式化后的日期时间,精确到毫秒  
        this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  
        return this.sdf.format(new Date());  
    }  
    public String getTimeStamp(){//取得时间戳  
        this.sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");  
        return this.sdf.format(new Date());  
    }  
    public String addZero(String str,int len){//不满位数，补0操作  
        StringBuffer buf = new StringBuffer();  
        buf.append(str);  
        while(buf.length()<len){  
            buf.insert(0,"0");  
        }  
        return buf.toString();  
    }  
}
