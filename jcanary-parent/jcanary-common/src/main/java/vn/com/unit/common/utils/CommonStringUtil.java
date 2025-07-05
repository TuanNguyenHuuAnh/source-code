/*******************************************************************************
 * Class        ：StringUtil
 * Created date ：2020/11/08
 * Lasted date  ：2020/11/08
 * Author       ：KhoaNA
 * Change log   ：2020/11/08：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.common.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

import vn.com.unit.dts.utils.DtsStringUtil;

/**
 * StringUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class CommonStringUtil extends DtsStringUtil{
	
	/**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     *
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is
     *  not empty and not null and not whitespace only
     * @since 2.0
     * @since 3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
     */
	public static boolean isNotBlank(final CharSequence cs) {
		return DtsStringUtil.isNotBlank(cs);
	}
	
	/**
     * <p>Repeat a String {@code repeat} times to form a
     * new String.</p>
     *
     * <pre>
     * StringUtils.repeat(null, 2) = null
     * StringUtils.repeat("", 0)   = ""
     * StringUtils.repeat("", 2)   = ""
     * StringUtils.repeat("a", 3)  = "aaa"
     * StringUtils.repeat("ab", 2) = "abab"
     * StringUtils.repeat("a", -2) = ""
     * </pre>
     *
     * @param str  the String to repeat, may be null
     * @param repeat  number of times to repeat str, negative treated as zero
     * @return a new String consisting of the original String repeated,
     *  {@code null} if null String input
     */
    public static String repeat(final String str, final int repeat) {
    	return DtsStringUtil.repeat(str, repeat);
    }
    
    /**
     * <p>Gets a substring from the specified String avoiding exceptions.</p>
     *
     * <p>A negative start position can be used to start {@code n}
     * characters from the end of the String.</p>
     *
     * <p>A {@code null} String will return {@code null}.
     * An empty ("") String will return "".</p>
     *
     * <pre>
     * StringUtils.substring(null, *)   = null
     * StringUtils.substring("", *)     = ""
     * StringUtils.substring("abc", 0)  = "abc"
     * StringUtils.substring("abc", 2)  = "c"
     * StringUtils.substring("abc", 4)  = ""
     * StringUtils.substring("abc", -2) = "bc"
     * StringUtils.substring("abc", -4) = "abc"
     * </pre>
     *
     * @param str  the String to get the substring from, may be null
     * @param start  the position to start from, negative means
     *  count back from the end of the String by this many characters
     * @return substring from start position, {@code null} if null String input
     */
    public static String substring(final String str, int start) {
    	return DtsStringUtil.substring(str, start);
    }
	
    /**
     * <p>Unicode standardized String</p>
     *
     * <p>Remove accents of characters  by this way to
     * characters in String to standardized 
     * .</p>
     *
     * <p>A {@code null} String will return NullPointerException.
     * An empty ("") String will return "".</p>
     *
     * <pre>
     * CommonStringUtil.removeAccent("Đa")   = "Da"
     * CommonStringUtil.removeAccent("abc#Đ")= "abc#D"
     * CommonStringUtil.removeAccent("á")   = "a"
     * CommonStringUtil.removeAccent("")   = ""
     * </pre>
     *
     * @param str  the String to remove special, not null
     * @return str the String belongs to a regular expression
     */
    public static String removeAccent(String str) {
        str = str.replaceAll("Đ", "D");
        str = str.replaceAll("đ", "d");
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }


    /**
     * <p>Remove Accents special for String.</p>
     *
     * <p>Finds all characters in String that relate to a special character 
     * that difference a regular expression {[^ A-Za-z0-9]}
     * after changing it becomes the accepted character.</p>
     *
     * <p>A {@code null} String will return NullPointerException.
     * An empty ("") String will return "".</p>
     *
     * <pre>
     * CommonStringUtil.removeSpecial("abc#")   = "abc"
     * CommonStringUtil.removeSpecial("abc#123")= "abc123"
     * CommonStringUtil.removeSpecial("123@")   = "123"
     * CommonStringUtil.removeSpecial("abc123") = "abc123"
     * CommonStringUtil.removeSpecial("")       = ""
     * CommonStringUtil.removeSpecial(" ")      = " "
     * </pre>
     *
     * @param str  the String to remove special, not null
     * @return str the String belongs to a regular expression
     */
    public static String removeSpecial(String str) {
        return str.replaceAll("[^A-Za-z0-9]","");
    }
}
