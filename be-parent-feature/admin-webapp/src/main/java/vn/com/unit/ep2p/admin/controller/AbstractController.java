package vn.com.unit.ep2p.admin.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import vn.com.unit.core.config.SystemConfig;

/**
 * @version 01-00
 * @author CongDT
 * @since Aug 28, 2017 11:28:36 AM
 */
public class AbstractController {

    @Autowired
    private SystemConfig systemConfig;

    @InitBinder
    public void dataFormatBinder(WebDataBinder binder, Locale locale, HttpServletRequest request) throws Exception {

	HttpSession httpSession = request.getSession();
	if (httpSession == null) {
	    throw new Exception("Session is not init");
	}

	SimpleDateFormat dateFormat = new SimpleDateFormat(systemConfig.getConfig(SystemConfig.DATE_PATTERN));
	dateFormat.setLenient(false);
	// Create a new CustomDateEditor
	CustomDateEditor customDateEditor = new CustomDateEditor(dateFormat, true);
	// Register it as custom editor for the Date type
	binder.registerCustomEditor(Date.class, customDateEditor);

	// Register it as custom editor for the Double type
	// DoubleEditor doubleEditor = new DoubleEditor(locale,
	// Util.PATTERN_MONEY);
	// binder.registerCustomEditor(BigDecimal.class, doubleEditor);

	// Binder BigDecimal value
	// NumberFormat numberFormat1 = new DecimalFormat(Util.PATTERN_MONEY);
	NumberFormat numberFormat = NumberFormat.getInstance(locale);
	CustomNumberEditor customNumberEditor = new CustomNumberEditor(BigDecimal.class, numberFormat, true);
	binder.registerCustomEditor(BigDecimal.class, customNumberEditor);
    }
}
