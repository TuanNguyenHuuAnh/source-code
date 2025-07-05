package vn.com.unit.cms.admin.all.jcanary.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.dto.PopupLanguageDto;
import vn.com.unit.cms.admin.all.dto.ProductLanguageSearchDto;
import vn.com.unit.cms.admin.all.jcanary.repository.CommonRepository;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.core.service.impl.CommonServiceImpl;

/**
 * CommonServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author nhutnn
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CmsCommonServiceImpl extends CommonServiceImpl implements CmsCommonService {

    @Autowired
    private CommonRepository commonRepository;

    /**
     * getMaxCode
     *
     * @param tableName
     * @param prefix
     * @author nhutnn
     */
    @Override
    public String getMaxCode(String tableName, String prefix) {
        SimpleDateFormat format = new SimpleDateFormat("YY");
        prefix = prefix + format.format(new Date());
        return commonRepository.getMaxCode(tableName, prefix);
    }
    /**
     * getMaxBannerCode
     *
     * @param tableName
     * @param prefix
     * @author longdch
     */
    @Override
    public String getMaxCodeYYMM(String tableName, String prefix) {
        SimpleDateFormat format = new SimpleDateFormat("YY");
        SimpleDateFormat formatMM = new SimpleDateFormat("MM");

        prefix = prefix + format.format(new Date()) +formatMM.format(new Date());
        return commonRepository.getMaxCode(tableName, prefix);
    }
    @Override
    public List<ProductLanguageSearchDto> getListProductMicrosite(String languageCode) {
        return commonRepository.getListProductMicrosite(languageCode);
    }

    /**
     * searchPopupByCondition 26/04/2019
     * 
     * @param PopupLanguageDto
     * @return
     * @author TranLTH
     */
    @Override
    public List<PopupLanguageDto> searchPopupByCondition(String languageCode, Date expiryDate) {
        return commonRepository.searchPopupByCondition(languageCode, expiryDate);
    }
}
