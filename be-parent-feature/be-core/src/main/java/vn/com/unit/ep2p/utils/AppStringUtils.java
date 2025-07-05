/*******************************************************************************
 * Class        ：AppStringUtils
 * Created date ：2019/04/25
 * Lasted date  ：2019/04/25
 * Author       ：HungHT
 * Change log   ：2019/04/25：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.Normalizer;
import java.util.regex.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * AppStringUtils
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 * @Last updated: 22/03/2024	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 */
public class AppStringUtils {

    /**
     * removeAccent
     * 
     * @param s
     * @return
     * @author HungHT
     */
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
    public static String formatPolicyNumber(int digits, String policyNumber){
        if(StringUtils.isEmpty(policyNumber)) {
            return "";
        }
        if(digits < policyNumber.length()) {
            return policyNumber;
        }
        return IntStream.range(0, digits - policyNumber.length()).mapToObj(i -> "0").collect(Collectors.joining("")).concat(policyNumber);
    }
    
    public static boolean checkByRegex(String text, String regex) {
        // Create a Pattern object
        Pattern r = Pattern.compile(regex);

        // Create a Matcher object
        Matcher m = r.matcher(text);

        // Check if the input text contains HTML tags
        return m.find();
    }
}
