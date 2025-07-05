package vn.com.unit.ep2p.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlSafeUtil {

    private static final String SQL_TYPES = "TABLE, TABLESPACE, PROCEDURE, FUNCTION, TRIGGER, KEY, VIEW, MATERIALIZED VIEW, LIBRARY"
            + "DATABASE LINK, DBLINK, INDEX, CONSTRAINT, TRIGGER, USER, SCHEMA, DATABASE, PLUGGABLE DATABASE, BUCKET, "
            + "CLUSTER, COMMENT, SYNONYM, TYPE, JAVA, SESSION, ROLE, PACKAGE, PACKAGE BODY, OPERATOR"
            + "SEQUENCE, RESTORE POINT, PFILE, CLASS, CURSOR, OBJECT, RULE, USER, DATASET, DATASTORE, "
            + "COLUMN, FIELD, OPERATOR";

    private static final String[] SQL_REGEXPS = { "(?i)(.*)(\\b)+(OR|AND)(\\s)+(true|false)(\\s)*(.*)",
            "(?i)(.*)(\\b)(.*)(\\=)(\\b)(.*)",
            "(?i)(.*)(\\s)(.*)(\\=)(\\s)(.*)",
            "(?i)(.*)(\\b)+(\\s)+(\\w)(\\s)*(\\=)(\\s)*(\\w)(\\s)*(.*)",
            "(?i)(.*)(\\b)+(OR|AND)(\\s)+(\\w)(\\s)*(\\=)(\\s)*(\\w)(\\s)*(.*)",
            "(?i)(.*)(\\b)+(OR|AND)(\\s)+(equals|not equals)(\\s)+(true|false)(\\s)*(.*)",
            "(?i)(.*)(\\b)+(OR|AND)(\\s)+([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(\\=)(\\s)*([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(.*)",
            "(?i)(.*)(\\b)+(OR|AND)(\\s)+([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(\\!\\=)(\\s)*([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(.*)",
            "(?i)(.*)(\\b)+(OR|AND)(\\s)+([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(\\<\\>)(\\s)*([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(.*)",
            "(?i)(.*)(\\b)+UNION(\\b)+\\s.*(\\b)(.*)",
            "(?i)(.*)(\\b)+SELECT(\\b)+\\s.*(\\b)(.*)",
            "(?i)(.*)(\\b)+SELECT(\\b)+\\s.*(\\b)+FROM(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+INSERT(\\b)+\\s.*(\\b)+INTO(\\b)+\\s.*(.*)", "(?i)(.*)(\\b)+UPDATE(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+DELETE(\\b)+\\s.*(\\b)+FROM(\\b)+\\s.*(.*)", "(?i)(.*)(\\b)+UPSERT(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+SAVEPOINT(\\b)+\\s.*(.*)", "(?i)(.*)(\\b)+CALL(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+ROLLBACK(\\b)+\\s.*(.*)", "(?i)(.*)(\\b)+KILL(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+DROP(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+CREATE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+ALTER(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+TRUNCATE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+LOCK(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+UNLOCK(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+RELEASE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+DESC(\\b)+(\\w)*\\s.*(.*)", "(?i)(.*)(\\b)+DESCRIBE(\\b)+(\\w)*\\s.*(.*)",
            "(.*)(/\\*|\\*/|;){1,}(.*)", "(.*)(-){2,}(.*)",

    };

    // pre-build the Pattern objects for faster validation
    // private static final List<Pattern> validationPatterns =
    // buildValidationPatterns();
    private static final List<Pattern> validationPatterns = buildPatterns(SQL_REGEXPS);

    /**
     * Determines if the provided string value is SQL-Injection-safe.
     * <p>
     * Empty value is considered safe. This is used in by the SqlInjectionSafe
     * annotation.
     *
     * @param dataString string value that is to verified
     * @return 'true' for unsafe and 'false' for safe
     */
    public static boolean isSqlInjectionUnSafe(String dataString) {
        if (isEmpty(dataString)) {
            return false;
        }

        for (Pattern pattern : validationPatterns) {
            if (matches(pattern, dataString)) {
                return true;
            }
        }
        return false;
    }

    private static boolean matches(Pattern pattern, String dataString) {
        Matcher matcher = pattern.matcher(dataString);
        return matcher.matches();
    }

    private static List<Pattern> buildPatterns(String[] expressionStrings) {
        List<Pattern> patterns = new ArrayList<>();
        for (String expression : expressionStrings) {
            patterns.add(getPattern(expression));
        }
        return patterns;
    }

    private static Pattern getPattern(String regEx) {
        return Pattern.compile(regEx, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    }

    private static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static Field[] populateFields(Class<?> cls) {
        Field[] fieldsSuper = cls.getSuperclass().getDeclaredFields();
        Field[] fieldsChild = cls.getDeclaredFields();
        int superLength = fieldsSuper.length;
        int childLength = fieldsChild.length;
        Field[] fields = new Field[superLength + childLength];
        System.arraycopy(fieldsSuper, 0, fields, 0, superLength);
        System.arraycopy(fieldsChild, 0, fields, superLength, childLength);
        return fields;
    }
}