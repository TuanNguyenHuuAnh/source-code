/*******************************************************************************
 * Class        ：JpmLanguageService
 * Created date ：2021/03/04
 * Lasted date  ：2021/03/04
 * Author       ：KhuongTH
 * Change log   ：2021/03/04：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.workflow.service;

import java.util.Map;

/**
 * <p>
 * JpmLanguageService
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface JpmLanguageService {

    /**
     * <p>
     * Gets the language id converter.
     * </p>
     *
     * @return the language id converter
     * @author KhuongTH
     */
    Map<String, Long> getLanguageIdConverter();
}
