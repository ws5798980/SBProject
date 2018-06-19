package com.rs.mobile.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DateString {  
    private static String mYear;  
    private static String mMonth;  
    private static String mDay;  
    private static String mWay;  
      
    public static String StringDateTime(int i){  
        final Calendar c = Calendar.getInstance();
        c.roll(java.util.Calendar.DAY_OF_YEAR,i);
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));  
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份  
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份  
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码  
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));  
        if("1".equals(mWay)){  
            mWay ="天";  
        }else if("2".equals(mWay)){  
            mWay ="一";  
        }else if("3".equals(mWay)){  
            mWay ="二";  
        }else if("4".equals(mWay)){  
            mWay ="三";  
        }else if("5".equals(mWay)){  
            mWay ="四";  
        }else if("6".equals(mWay)){  
            mWay ="五";  
        }else if("7".equals(mWay)){  
            mWay ="六";  
        }  
        return  mMonth + "月" + mDay+"日";  
    } 
    public static String StringWeek(int i){
    	final Calendar c=Calendar.getInstance();
    	c.roll(java.util.Calendar.DAY_OF_YEAR,i);
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));  

    	mWay=String.valueOf(c.get(Calendar.DAY_OF_WEEK));
    	 if("1".equals(mWay)){  
             mWay ="天";  
         }else if("2".equals(mWay)){  
             mWay ="一";  
         }else if("3".equals(mWay)){  
             mWay ="二";  
         }else if("4".equals(mWay)){  
             mWay ="三";  
         }else if("5".equals(mWay)){  
             mWay ="四";  
         }else if("6".equals(mWay)){  
             mWay ="五";  
         }else if("7".equals(mWay)){  
             mWay ="六";  
         } 
    	 return "星期"+mWay;
    }
    public static int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    }     
}  
