
package vn.com.unit.ep2p.admin.binding;

import java.beans.PropertyEditorSupport;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * DoubleEditor
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class DoubleEditor extends PropertyEditorSupport {
    
    /** locale */
	private Locale locale;
	
	/** pattern */
	private String pattern;
	
	/**
	 * Constructor default
	 * 
	 * @param locale
	 *         type Locale
	 * @param pattern
	 *         type Locale
	 * @author KhoaNA
	 */
	public DoubleEditor(Locale locale, String pattern) {
		super();
		this.locale = locale;
		this.pattern = pattern;
	}
	
	/**
     * Get locale
     * @return Locale
     * @author KhoaNA
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Set locale
     * @param   locale
     *          type Locale
     * @return
     * @author  KhoaNA
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Get pattern
     * @return String
     * @author KhoaNA
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Set pattern
     * @param   pattern
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Get as text
     * 
     * @return String
     * @author KhoaNA
     */
    @Override
	public String getAsText() {
        String result = null;
        
        if( getValue() != null ) {
            NumberFormat nf = NumberFormat.getNumberInstance(locale);
            DecimalFormat df = (DecimalFormat)nf;
            df.applyPattern(pattern);
            result = df.format(getValue());
        }
		return result;
	}
    
    /**
     * Set as text
     * 
     * @param text
     *          type String
     * @return
     * @exception IllegalArgumentException
     * @author KhoaNA
     */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(StringUtils.isEmpty(text)) {
		    setValue(null);
		} else {
			try {
			    NumberFormat nf = NumberFormat.getNumberInstance(locale);
				DecimalFormat df = (DecimalFormat)nf;
				df.applyPattern(pattern);
				setValue(df.parse(text).doubleValue());
			}
			catch (ParseException ex) {
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
			}
		}
		
	}
}
