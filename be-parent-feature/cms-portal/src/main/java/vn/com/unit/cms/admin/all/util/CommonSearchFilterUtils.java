package vn.com.unit.cms.admin.all.util;

import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import vn.com.unit.cms.core.utils.CmsDateUtils;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;

@Component
public class CommonSearchFilterUtils {

    @Autowired
    private Select2DataService select2DataService;

    private final String SEARCH_FILTER = "SEARCH_FILTER";
    private final String INPUT_KIND = "INPUT";
    private final String SELECT_KIND = "SELECT";
    private final String DATE_KIND = "DATE";

    @Autowired
    private MessageSource msg;
	private static final Logger logger = LoggerFactory.getLogger(CommonSearchFilterUtils.class);

    public CommonSearchFilterDto createInputCommonSearchFilterDto(String field, String fieldName, String value) {
        CommonSearchFilterDto data = new CommonSearchFilterDto();
        data.setField(field);
        data.setFieldName(fieldName);
        data.setType("INPUT");
        data.setValue(value);

        List<Select2Dto> listSelectCondition = select2DataService.getConstantData(SEARCH_FILTER, INPUT_KIND,
                UserProfileUtils.getLanguage());
        data.setListSelectCondition(listSelectCondition);

        return data;
    }

    public CommonSearchFilterDto createSelectCommonSearchFilterDto(String field, String fieldName, String value,
            List<Select2Dto> lstData) {
        CommonSearchFilterDto data = new CommonSearchFilterDto();
        data.setField(field);
        data.setFieldName(fieldName);
        data.setType("SELECT");
        data.setValue(value);
        data.setListSelect(lstData);

        List<Select2Dto> listSelectCondition = select2DataService.getConstantData(SEARCH_FILTER, SELECT_KIND,
                UserProfileUtils.getLanguage());
        data.setListSelectCondition(listSelectCondition);

        return data;
    }

    public CommonSearchFilterDto createCheckboxCommonSearchFilterDto(String field, String fieldName,
            boolean isChecked) {
        CommonSearchFilterDto data = new CommonSearchFilterDto();
        data.setField(field);
        data.setFieldName(fieldName);
        data.setType("CHECKBOX");
        data.setChecked(isChecked);

        List<Select2Dto> listSelectCondition = select2DataService.getConstantData(SEARCH_FILTER, SELECT_KIND,
                UserProfileUtils.getLanguage());
        data.setListSelectCondition(listSelectCondition);

        return data;
    }

    public CommonSearchFilterDto createDateCommonSearchFilterDto(String field, String fieldName, Date value) {
        CommonSearchFilterDto data = new CommonSearchFilterDto();
        data.setField(field);
        data.setFieldName(fieldName);
        data.setType("DATE");
        data.setValueDate(value);

        List<Select2Dto> listSelectCondition = select2DataService.getConstantData(SEARCH_FILTER, DATE_KIND,
                UserProfileUtils.getLanguage());
        data.setListSelectCondition(listSelectCondition);

        return data;
    }
    
    public CommonSearchFilterDto createDateCommonSearchFilterDto(String field, String fieldName, String value) {
        CommonSearchFilterDto data = new CommonSearchFilterDto();
        data.setField(field);
        data.setFieldName(fieldName);
        data.setType("DATE");

        try {
            if (StringUtils.isNotBlank(value)) {
                data.setValueDate(CmsDateUtils.formatStringToDate(value, "dd/MM/yyyy"));
            }
        } catch (ParseException e) {
            logger.error("Exception ", e);
        }

        List<Select2Dto> listSelectCondition = select2DataService.getConstantData(SEARCH_FILTER, DATE_KIND,
                UserProfileUtils.getLanguage());
        data.setListSelectCondition(listSelectCondition);

        return data;
    }

    /**
     * Giành cho một vài trường hợp cụ thể
     */
    public CommonSearchFilterDto createSelectStatusProcess(String field, String value, String businessCode,
            Locale locale) {
        CommonSearchFilterDto data = new CommonSearchFilterDto();
        data.setField(field);

        String fieldName = msg.getMessage("searchfield.disp.status", null, locale);
        data.setFieldName(fieldName);
        data.setType("SELECT");
        data.setValue(value);

        List<Select2Dto> listDataStatus = select2DataService.getListStatusForProcess(businessCode, locale.toString(),
                UserProfileUtils.getCompanyId());
        
        // xoá bước gửi duyệt
        Iterator<Select2Dto> iter = listDataStatus.iterator();
        while (iter.hasNext()) {
            Select2Dto select = iter.next();
            if ("taskSubmit".equals(select.getId())) {
                iter.remove();
            }
        }
        
        data.setListSelect(listDataStatus);

        List<Select2Dto> listSelectCondition = select2DataService.getConstantData(SEARCH_FILTER, SELECT_KIND,
                UserProfileUtils.getLanguage());
        data.setListSelectCondition(listSelectCondition);

        return data;
    }
}
