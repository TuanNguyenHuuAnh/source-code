package vn.com.unit.ep2p.utils;

import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author TaiTM
 **/

public class LangugeUtil {
    public static Locale getLanguageFromHeader(HttpServletRequest request) {
        Locale locale = new Locale(Optional.ofNullable(request.getHeader("Accept-Language")).orElse("vi"));
        if (locale.toString().contains("vi")) {
            locale = new Locale("vi");
        } else {
            locale = new Locale("en");
        }
        return locale;
    }
}
