package vn.com.unit.ep2p.core.utils;

import java.util.regex.Pattern;

public class RegexPasswordUtils {
    public static String REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
    
    public static boolean isRegex(String password) {
        boolean matcher = Pattern.matches(REGEX, password);
        if (matcher) {
            return true;
        } else {
            return false;
        }
    }

}
