package com.xiaowu.util;

import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

	
	/**
	 * 得到四位数的随机数
	 * @return
	 */
	public static int getFourRandom(){
		int max=9999;
        int min=1000;
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
	}
	
	/**
	 * 是否是字母
	 * @param str
	 * @return
	 */
	public static boolean isLetter(String str){

		String regex=".*[a-zA-Z]+.*";
		Matcher m=Pattern.compile(regex).matcher(str);

	   return m.matches();

	}
	
	/**
	 * 获取UUID
	 * @return
	 */
	public static String getUUID(){
		 UUID uuid = UUID.randomUUID();
		 return uuid.toString().replace("-", "");
	}
	
	
	public static String filterEmoji(String source) { 
        if(source != null)
        {
            Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern . CASE_INSENSITIVE ) ;
            Matcher emojiMatcher = emoji.matcher(source);
            if ( emojiMatcher.find())
            {
                source = emojiMatcher.replaceAll("");
                return source ;
            }
        return source;
       }
       return source; 
    }
	
}
