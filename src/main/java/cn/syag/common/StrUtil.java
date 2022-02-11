package cn.syag.common;

/**
 * 字符串检测
 */
public class StrUtil {
    public static boolean isBank(String content){
       if (null==content){
           return true;
       }
        for (char c : content.toCharArray()) {
            if (c!=' '){
                return false;
            }
        }
       return true;
    }
    public static boolean isNotBank(String content){
        return !isBank(content);
    }
}
