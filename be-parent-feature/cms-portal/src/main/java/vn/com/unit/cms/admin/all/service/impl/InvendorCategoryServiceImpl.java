package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.dto.InvendorCategoryDto;
import vn.com.unit.cms.admin.all.dto.InvendorCategoryLanguageDto;
import vn.com.unit.cms.admin.all.dto.SortPageDto;
import vn.com.unit.cms.admin.all.enumdef.DocumentProcessEnum;
import vn.com.unit.cms.admin.all.enumdef.InvendorCategoryProcessEnum;
import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
import vn.com.unit.cms.admin.all.service.InvendorCategoryService;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.core.entity.Language;
import vn.com.unit.core.repository.LanguageRepository;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class InvendorCategoryServiceImpl extends
        DocumentWorkflowCommonServiceImpl<InvendorCategoryDto, InvendorCategoryDto> implements InvendorCategoryService {

	@Autowired
	private LanguageRepository languageRepository;
	
//	@Autowired
//	private ProcessService processService;

	@Override
	public PageWrapper<InvendorCategoryDto> getAllActive(int page, int intConfig) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SearchKeyDto> genSearchKeyList(Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countByCode(String code) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public InvendorCategoryDto getDetailById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageWrapper<InvendorCategoryDto> getActiveByConditions(int page, int intConfig, CommonSearchDto searchDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InvendorCategoryDto saveNew(InvendorCategoryDto updateDto, Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InvendorCategoryDto approve(InvendorCategoryDto invendorCategoryDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InvendorCategoryDto reject(InvendorCategoryDto invendorCategoryDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InvendorCategoryDto submit(InvendorCategoryDto invendorCategoryDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InvendorCategoryDto saveUpdate(InvendorCategoryDto updateModel, InvendorCategoryProcessEnum create) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InvendorCategoryDto initInvendorCategoryDto() {
		InvendorCategoryDto detailDto = new InvendorCategoryDto();
        List<InvendorCategoryLanguageDto> lstInfoByLanguage = new ArrayList<InvendorCategoryLanguageDto>();
        List<Language> languageList = languageRepository.findAllActive();
        for (Language lang : languageList) {
        	InvendorCategoryLanguageDto documentCateLangDto = new InvendorCategoryLanguageDto();
            documentCateLangDto.setLanguageCode(lang.getCode());
            documentCateLangDto.setLanguageDispName(lang.getName());
            lstInfoByLanguage.add(documentCateLangDto);
            detailDto.setInfoByLanguages(lstInfoByLanguage);
        }
        detailDto.setStatus(DocumentProcessEnum.CREATE.toString());
//        detailDto.setProcessId(processService.getProcessIdByBusinessCode(MasterType.AD1.toString()));
        return detailDto;
    }

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<InvendorCategoryDto> getAllActive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateModelsSort(SortPageDto sortPageModel) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public InvendorCategoryDto getEdit(Long id, String customerAlias, Locale locale) {
        // TODO Auto-generated method stub
        return null;
    }
}
