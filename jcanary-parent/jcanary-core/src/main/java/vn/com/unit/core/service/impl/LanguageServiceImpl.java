/*******************************************************************************
 * Class        LanguageServiceImpl
 * Created date 2017/02/16
 * Lasted date  2017/02/16
 * Author       NhanNV
 * Change log   2017/02/1601-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.entity.Language;
import vn.com.unit.core.repository.LanguageRepository;
import vn.com.unit.core.service.LanguageService;

/**
 * LanguageServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class LanguageServiceImpl implements LanguageService {

	@Autowired
	private LanguageRepository languageRepository;
	
	// Object mapper
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * find all Language
	 *
	 * @return Language List
	 * @author NhanNV
	 */
	@Override
	public List<Language> findAllActive() {
		return languageRepository.findAllActive();
	}

	/**
	 * get data language for select box
	 *
	 * @return LanguageDto List
	 * @author NhanNV
	 */
    @Override
    public List<LanguageDto> getLanguageDtoList() {
        List<Language> languages = languageRepository.findAllActive();
        List<LanguageDto> languageList = new ArrayList<>();
        for (Language language : languages) {
            LanguageDto languageDto = objectMapper.
            		convertValue(language, LanguageDto.class);
            languageDto.setSort(language.getSort() == null ? 0L : language.getSort());
            
            languageList.add(languageDto);
        }
        return languageList;
    }
    
    @Override
    public List<LanguageDto> getList() {
        List<Language> languages = languageRepository.findAllActive();
        List<LanguageDto> languageList = new ArrayList<>();
        for (Language language : languages) {
            LanguageDto languageDto = objectMapper.
            		convertValue(language, LanguageDto.class);
            languageDto.setSort(language.getSort() == null ? 0L : language.getSort());
            
            languageList.add(languageDto);
        }
        return languageList;
    }
	
    /**
     * findByCode
     * 
     * @param lang
     * @return
     * @author NhanNV
     */
    public LanguageDto findByCode(String lang) {
        return languageRepository.findByCode(lang);
    }
    
    /**
     * Get the language by company id and code
     * 
     * @param lang
     * @return
     * @author NhanNV
     */
    public LanguageDto findByCompanyIdAndCode(Long companyId, String lang) {
        return languageRepository.findByCompanyIdAndCode(companyId, lang);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.LanguageService#findByCompanyId(java.lang.Long)
     */
    @Override
    public List<LanguageDto> getListByCompanyId(Long companyId) {
        List<Language> languages = languageRepository.getByCompanyId(companyId);
        List<LanguageDto> languageList = new ArrayList<>();
        for (Language language : languages) {
            LanguageDto languageDto = objectMapper.
                    convertValue(language, LanguageDto.class);
            languageDto.setSort(language.getSort() == null ? 0L : language.getSort());
            
            languageList.add(languageDto);
        }
        return languageList;
       
    }   
}
