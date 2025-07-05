package vn.com.unit.ep2p.core.utils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailUtils {

    private static final Logger logger = LoggerFactory.getLogger(MailUtils.class);
    
    private static final String PARAM_START_WITH = "${";
    private static final String PARAM_END_WITH = "}";
    private static final Pattern PARAM_PATTERN = Pattern
            .compile(Pattern.quote(PARAM_START_WITH) + "(.*?)" + Pattern.quote(PARAM_END_WITH));
    
    public static String replaceParam(String content, Map<String, Object> mapData) {
        LinkedHashSet<String> hashParam = new LinkedHashSet<String>();
        hashParam.addAll(getParams(content));
        for (String param : hashParam) {
            if (mapData.containsKey(param)) {
                String text = (String) mapData.get(param);
                if (text != null) {
                    try {
                        String str = PARAM_START_WITH.concat(param).concat(PARAM_END_WITH);
                        while (content.contains(str)) {
                            content = content.replace(str, text);
                        }
                    } catch (Exception e) {
                        logger.error("######replaceParam######", e);
                    }
                }
            }
        }
        return content;
    }
    
    public static List<String> getParams(String content) {
        List<String> paramList = new ArrayList<String>();
        if (StringUtils.isNotBlank(content)) {
            Matcher matcher = PARAM_PATTERN.matcher(content);
            while (matcher.find()) {
                String param = matcher.group(1);
                paramList.add(param);
            }
        }
        return paramList;
    }
}
