/*******************************************************************************
 * Class        SearchUtil
 * Created date 2017/03/18
 * Lasted date  2017/03/18
 * Author       thuydtn
 * Change log   2017/03/1801-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.ep2p.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.common.dto.SelectItem;

/**
 * SearchUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class SearchUtil {

    /**
     * @param searchFieldIds
     * @param searchFieldDispNames
     * @param locale
     * @param msg
     * @return
     */
    public static List<SearchKeyDto> genSearchKeyList(String[] searchFieldIds, String[] searchFieldDispNames, Locale locale, MessageSource msg) {
        List<SearchKeyDto> searchKeys = new ArrayList<SearchKeyDto>();

        for (int index = 0; index < searchFieldIds.length; ++index) {
            String searchFieldId = searchFieldIds[index];
            String searchFieldNameAtt = searchFieldDispNames[index];
            String searchFieldName = msg.getMessage(searchFieldNameAtt, null, locale);
            SearchKeyDto key = new SearchKeyDto();
            key.setFieldId(searchFieldId);
            key.setDispName(searchFieldName);
            searchKeys.add(key);
        }
        return searchKeys;
    }

    public static <E extends Enum<E>> List<SearchKeyDto> genSearchKeyList(Class<E> enumType, MessageSource msg, Locale locale) {
        List<SearchKeyDto> searchKeys = new ArrayList<SearchKeyDto>();

        for (E en : enumType.getEnumConstants()) {
            String searchFieldId = en.name();
            String searchFieldNameAtt = en.toString();
            String searchFieldName = msg.getMessage(searchFieldNameAtt, null, locale);
            SearchKeyDto key = new SearchKeyDto();
            key.setFieldId(searchFieldId);
            key.setDispName(searchFieldName);
            searchKeys.add(key);
        }
        return searchKeys;
    }
    
    /**
     * set SearchSelect by enum
     *
     * @param enumType
     * 			type Class<E>
     * @param model
     * 			type ModelMap
     * @author KhoaNA
     */
    public static <E extends Enum<E>> void setSearchSelect(Class<E> enumType, ModelMap model) {
        // fieldSelect
        List<SelectItem> fieldSelect = getSearchSelect(enumType);

        model.addAttribute("fieldSelect", fieldSelect);
    }

    /**
     * set SearchSelect by enum
     *
     * @param enumType
     *            Enum
     * @param mav
     * @author hand
     */
    public static <E extends Enum<E>> void setSearchSelect(Class<E> enumType, ModelAndView mav) {
        // fieldSelect
        List<SelectItem> fieldSelect = getSearchSelect(enumType);

        mav.addObject("fieldSelect", fieldSelect);
    }

    /**
     * get SelectItem List by Enum class
     *
     * @param enumType
     *            enum
     * @return fieldSelectList List<SelectItem>
     * @author hand
     */
    public static <E extends Enum<E>> List<SelectItem> getSearchSelect(Class<E> enumType) {

        // fieldSelect
        List<SelectItem> fieldSelectList = new ArrayList<SelectItem>();

        // loop enum
        for (E en : enumType.getEnumConstants()) {
            SelectItem item = new SelectItem(en.name(), en.toString());
            fieldSelectList.add(item);
        }

        return fieldSelectList;
    }
}
