package vn.com.unit.cms.admin.all.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.service.LanguageService;

@Component
public class CmsLanguageUtils {

    private static LanguageService languageService;

    @Autowired
    public CmsLanguageUtils(LanguageService languageService) {
        CmsLanguageUtils.languageService = languageService;
    }

    public static void initLanguageList(ModelAndView mav) {
        // Init master data
        List<LanguageDto> languageList = getLanguageList();
        mav.addObject("languageList", languageList);
    }

    public static List<LanguageDto> getLanguageList() {
        List<LanguageDto> languageList = languageService.getLanguageDtoList();

        // chổ này chỉ lấy ra 1 ngôn ngữ
        List<LanguageDto> languageListTmp = new ArrayList<>();
        languageListTmp.add(languageList.get(0));
        languageList = languageListTmp;

        return languageList;
    }
}
