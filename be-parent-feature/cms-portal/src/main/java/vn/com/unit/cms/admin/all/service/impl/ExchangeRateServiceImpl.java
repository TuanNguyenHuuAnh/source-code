/*******************************************************************************
 * Class        ：ExchangeRateServiceImpl
 * Created date ：2017/04/19
 * Lasted date  ：2017/04/26
 * Author       ：phunghn
 * Change log   ：2017/04/26：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.dto.CurrencyDto;
import vn.com.unit.cms.admin.all.dto.ExchangeRateDto;
import vn.com.unit.cms.admin.all.dto.ExchangeRateListDto;
import vn.com.unit.cms.admin.all.dto.ExchangeRateSearchDto;
import vn.com.unit.cms.admin.all.dto.ExchnageRateHistoryDto;
import vn.com.unit.cms.admin.all.entity.ExchangeRate;
import vn.com.unit.cms.admin.all.entity.ExchnageRateHistory;
import vn.com.unit.cms.admin.all.repository.CurrencyRepository;
import vn.com.unit.cms.admin.all.repository.ExchangeRateServiceRepository;
import vn.com.unit.cms.admin.all.repository.ExchnageRateHistoryRepository;
import vn.com.unit.cms.admin.all.service.ExchangeRateService;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.config.SystemConfig;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.ep2p.core.utils.Utility;

/**
 * ExchangeRateServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ExchangeRateServiceImpl implements ExchangeRateService {
    @Autowired
    ExchangeRateServiceRepository exRateRepository;

    @Autowired
    ExchnageRateHistoryRepository exRateHisotryRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    private SystemConfig systemConfig;

    private final static String Zero = "0";

    /**
     * findAllActive
     *
     * @param page
     * @param exRateDto
     * @return PageWrapper<ExchangeRateDto>
     * @author phunghn
     */
    @Override
    public PageWrapper<ExchangeRateDto> findAllActive(int page, ExchangeRateSearchDto exRateDto) {
        int sizeOfPage = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
        PageWrapper<ExchangeRateDto> pageWrapper = new PageWrapper<ExchangeRateDto>(page, sizeOfPage);

        int countExchangeRate = exRateRepository.countFindAllActive(exRateDto);
        List<ExchangeRateDto> listExRate = new ArrayList<ExchangeRateDto>();
        if (countExchangeRate > 0) {
            int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
            listExRate = exRateRepository.findAllActive(offsetSQL, sizeOfPage, exRateDto);
            // get currency name
            for (ExchangeRateDto item : listExRate) {
                CurrencyDto curDto = new CurrencyDto();
                curDto = currencyRepository.findByIdLangeCode(item.getmCurrencyId(),
                        exRateDto.getLangCode().toUpperCase());
                if(curDto != null && curDto.getName() != null){
                    item.setmCurrencyName(curDto.getName());    
                }                
            }
        }
        pageWrapper.setDataAndCount(listExRate, countExchangeRate);

        return pageWrapper;
    }

    /**
     * getListExchangeRateById
     *
     * @param id
     * @param langeCode
     * @return List<ExchangeRateDto>
     * @author phunghn
     */
    @Override
    public List<ExchangeRateDto> getListExchangeRateById(Long id, String langeCode) {        
        List<ExchangeRateDto> list = new ArrayList<ExchangeRateDto>();
        if (id == null) {
            list = getListExchangeRateMaxDay(langeCode);
        }
        return list;
    }
    /**
     * getListExchangeRateMaxDay
     *
     * @param langCode
     * @return List<ExchangeRateDto>
     * @author phunghn
     */
    private List<ExchangeRateDto> getListExchangeRateMaxDay(String langCode) {
        List<ExchangeRateDto> list = new ArrayList<ExchangeRateDto>();
        // get max date
        List<ExchangeRateDto> listExRate = new ArrayList<ExchangeRateDto>();
        listExRate = exRateRepository.findAllActive(0, 1, new ExchangeRateSearchDto());
        ExchangeRateDto exRateDto = new ExchangeRateDto();
        if (listExRate.size() > 0) {
            exRateDto = listExRate.get(0);
        }
        // get list currency
        List<CurrencyDto> listCurrency = new ArrayList<CurrencyDto>();
        listCurrency = currencyRepository.findByLangeCode(langCode);
        // set to list List<ExchangeRateDto
        for (CurrencyDto item : listCurrency) {
            ExchangeRateDto exRateDtoI = new ExchangeRateDto();
            exRateDtoI.setmCurrencyId(item.getId());
            exRateDtoI.setmCurrencyName(item.getName());
            if (exRateDto == null) {
                exRateDtoI.setBuying(Zero);
                exRateDtoI.setTransfer(Zero);
                exRateDtoI.setSelling(Zero);
            } else {
                ExchangeRateDto exRateDtoDb = new ExchangeRateDto();
                exRateDtoDb = exRateRepository.getExchangeRateByCurrencyMaxDate(item.getId(),
                        exRateDto.getDisplayDate());
                if (exRateDtoDb != null) {
                    exRateDtoI.setBuying(exRateDtoDb.getBuying());
                    exRateDtoI.setTransfer(exRateDtoDb.getTransfer());
                    exRateDtoI.setSelling(exRateDtoDb.getSelling());
                } else {
                    exRateDtoI.setBuying(Zero);
                    exRateDtoI.setTransfer(Zero);
                    exRateDtoI.setSelling(Zero);
                }
            }
            list.add(exRateDtoI);
        }
        return list;
    }

    /**
     * CreateOrEditExchangeRate
     *
     * @param exchangeRateListDto
     * @author phunghn
     */
    @Override
    @Transactional
    public void CreateOrEditExchangeRate(ExchangeRateListDto exchangeRateListDto) {
        String userName = UserProfileUtils.getUserNameLogin();
        // check dayDisplay
        Date currentDate = null;
        currentDate = getDateCurrent();
        //
        if (exchangeRateListDto.getExchangeRateId() == null) { // insert or edit
            Long id = getMaxId();
            List<ExchangeRateDto> listExchangeRate = new ArrayList<ExchangeRateDto>();
            listExchangeRate = exchangeRateListDto.getData();
            for (ExchangeRateDto i : listExchangeRate) {
                // check currencyID exists
                CurrencyDto currencyDto = new CurrencyDto();
                currencyDto = currencyRepository.findByIdLangeCode(i.getmCurrencyId(),
                        exchangeRateListDto.getLangCode().toUpperCase());
                if (currencyDto != null) {
                    // check mCurrency & display_date =>exists
                    ExchangeRateDto exRateDtoDb = new ExchangeRateDto();
                    exRateDtoDb = exRateRepository.getExchangeRateByCurrencyMaxDate(i.getmCurrencyId(), currentDate);
                    ExchangeRate entity = new ExchangeRate();
                    entity.setmCurrencyId(i.getmCurrencyId());
                    entity.setBuying(i.getBuying() == null ? Zero : i.getBuying().replace(",",""));
                    entity.setTransfer(i.getTransfer() == null ? Zero : i.getTransfer().replace(",",""));
                    entity.setSelling(i.getSelling() == null ? Zero : i.getSelling().replace(",",""));
                    //
                    // save log history
                    ExchnageRateHistory entityExRateHistory = new ExchnageRateHistory();
                    entityExRateHistory.setmCurrencyId(i.getmCurrencyId());
                    entityExRateHistory.setBuying(i.getBuying());
                    entityExRateHistory.setTranfers(i.getTransfer());
                    entityExRateHistory.setSelling(i.getSelling());
                    entityExRateHistory.setmRxchangeRateId(id);
                    entityExRateHistory.setCreateBy(userName);
                    entityExRateHistory.setCreateDate(new Date());
                    if (exRateDtoDb == null) { // insert
                        entity.setId(id);
                        entity.setCreateBy(userName);
                        entity.setCreateDate(new Date());
                        entity.setDisplayDate(currentDate);
                        exRateRepository.insertExchangeRate(entity);
                        //
                        entityExRateHistory.setUpdateDateTime(currentDate);
                    } else { // update
                        entity.setId(exRateDtoDb.getId());
                        entity.setUpdateBy(userName);
                        entity.setUpdateDate(new Date());
                        entity.setDisplayDate(exRateDtoDb.getDisplayDate());
                        exRateRepository.updateExchangeRate(entity);
                        //
                        entityExRateHistory.setUpdateDateTime(exRateDtoDb.getDisplayDate());
                    }
                    exRateHisotryRepository.save(entityExRateHistory);
                }
            }
        }
    }
    /**
     * getMaxId
     *
     * @return Long
     * @author phunghn
     */
    private Long getMaxId() {
        List<ExchangeRateDto> listExRate = new ArrayList<ExchangeRateDto>();
        listExRate = exRateRepository.getMaxId(0, 1, new ExchangeRateSearchDto());
        if (listExRate.size() > 0) {
            ExchangeRateDto exRateDto = new ExchangeRateDto();
            exRateDto = listExRate.get(0);
            Long id = exRateDto.getId();
            return (long) id + 1;
        } else {
            return (long) 0;
        }
    }
    /**
     * getDateCurrent
     *
     * @return Date
     * @author phunghn
     */
    private Date getDateCurrent() {
        try {
            Date dateNow = new Date();
            // SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
            // return sdf.parse(sdf.format(dateNow));
            return dateNow;
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     * findAllActiveHistory
     *
     * @param page
     * @param exRateDto
     * @return PageWrapper<ExchnageRateHistoryDto>
     * @author phunghn
     */

    @Override
    public PageWrapper<ExchnageRateHistoryDto> findAllActiveHistory(int page, ExchangeRateSearchDto exRateDto) {
        int sizeOfPage = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
        PageWrapper<ExchnageRateHistoryDto> pageWrapper = new PageWrapper<ExchnageRateHistoryDto>(page, sizeOfPage);

        int countExchangeRate = exRateHisotryRepository.countFindAllActive(exRateDto);
        List<ExchnageRateHistoryDto> listExRate = new ArrayList<ExchnageRateHistoryDto>();
        if (countExchangeRate > 0) {
            int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
            listExRate = exRateHisotryRepository.findAllActive(offsetSQL, sizeOfPage, exRateDto);
            // get currency name
            for (ExchnageRateHistoryDto item : listExRate) {
                CurrencyDto curDto = new CurrencyDto();
                curDto = currencyRepository.findByIdLangeCode(item.getmCurrencyId(),
                        exRateDto.getLangCode().toUpperCase());
                item.setmCurrencyName(curDto.getName());
            }
        }
        pageWrapper.setDataAndCount(listExRate, countExchangeRate);

        return pageWrapper;
    }

}
