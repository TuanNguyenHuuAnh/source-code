package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.core.module.libraryMaterial.dto.LibraryMaterialSearchResultDto;
import vn.com.unit.cms.core.module.libraryMaterial.dto.LibraryMaterialSuperDto;
import vn.com.unit.cms.core.module.libraryMaterial.repository.LibraryMaterialDocumentRepository;
import vn.com.unit.cms.core.module.libraryMaterial.repository.LibraryMaterialRepository;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.enumdef.AccountTypeEnum;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.ep2p.core.utils.Utility;
import vn.com.unit.ep2p.service.ApiLibraryMaterialService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApiLibraryMaterialServiceImpl  implements ApiLibraryMaterialService{
	
    @Autowired
    private LibraryMaterialRepository libraryMaterialRepository;
    
    @Autowired
    private LibraryMaterialDocumentRepository libraryMaterialDocumentRepository;

    @Autowired
    private JcaAccountService jcaAccountService;

	@Override
	public int countLibraryMaterialByIdCategory(String searchKey, Long categoryId, Integer modeView, String language) {
		return libraryMaterialDocumentRepository.countLibraryMaterialByIdCategory(searchKey, categoryId, modeView, language);
	}

	@Override
	public List<LibraryMaterialSearchResultDto> searchLibraryMaterialByIdCategory(String searchKey, Long categoryId,
			Integer page, Integer size, String language, Integer modeView) {
		int offsetSQL = Utility.calculateOffsetSQL(page, size);
		List<LibraryMaterialSearchResultDto> listData = libraryMaterialDocumentRepository.searchLibraryMaterialByIdCategory(offsetSQL, size, searchKey, categoryId,language, modeView);

		return listData;
	}

	@Override
	public List<LibraryMaterialSuperDto> getListSuperCategory(Long parentId,Locale language, String categoryType) {
		String agentCode = UserProfileUtils.getFaceMask();
		List<JcaAccount> lstAccount =  jcaAccountService.getListByUserName(agentCode);
		String channel = null;
		List<String> partners = null;
		if ("gaProcess".equals(categoryType)) {
			categoryType = "AQP2112.0001";
		}
		if(!lstAccount.isEmpty()){
			String accountType = lstAccount.get(0).getAccountType();
			channel = lstAccount.get(0).getChannel();
			if ("AD".equals(UserProfileUtils.getChannel()) && "EBR2115.0001".equals(categoryType)) {
				String partner = lstAccount.get(0).getPartner();
				partners = new ArrayList<String>(Arrays.asList(partner.split(",")));
			}
			if(StringUtils.equalsIgnoreCase(accountType, AccountTypeEnum.TYPE_CANDIDATE.toString()))
				return libraryMaterialRepository.getListSuperCategory(parentId,language.toString(), true, categoryType, channel, partners);
		}
		return libraryMaterialRepository.getListSuperCategory(parentId,language.toString(), false, categoryType, channel, partners);
	}

	@Override
	public ObjectDataRes<LibraryMaterialSuperDto> getListSubDoc(Long id, Locale locale, Integer page, Integer pageSize, String keySearch) {
		ObjectDataRes<LibraryMaterialSuperDto> rs = new ObjectDataRes<>();
		int offsetSQL = calculateOffsetSQL(page, pageSize);
		int count  = libraryMaterialRepository.countSubCategoryById(id, locale.toString(), keySearch, UserProfileUtils.getChannel());
		List<LibraryMaterialSuperDto> lstData = libraryMaterialRepository.findSubCategoryById(id, locale.toString(), offsetSQL, pageSize, keySearch, UserProfileUtils.getChannel());
		lstData.forEach(e -> {
			e.setTypeOfFile(getTypeOfFile(e.getPhysicalFileName()));
			e.setPhysicalFileName(e.getPhysicalFileName().replace("//", "/"));
		});
		rs.setDatas(lstData);
		rs.setTotalData(count);
		return rs;
	}
	private String getTypeOfFile(String filePath){
		return filePath.substring(filePath.lastIndexOf("."));
	}
	public int calculateOffsetSQL(int page, int sizeOfPage) {
		return page*sizeOfPage;
	}
	@Override
	public LibraryMaterialSuperDto getCategoryByConstant(String constantCode, Locale locale) {
		return libraryMaterialRepository.getCategoryByConstant(constantCode, locale.toString());
	}

	@Override
	public ObjectDataRes<LibraryMaterialSuperDto> getListSubDocGa(Locale locale, Integer page, Integer pageSize,
			String keySearch) {
		ObjectDataRes<LibraryMaterialSuperDto> rs = new ObjectDataRes<>();
		int offsetSQL = Utility.calculateOffsetSQL(page, pageSize);
		List<LibraryMaterialSuperDto> lstData = libraryMaterialRepository.findSubCategoryGa(locale.toString(), offsetSQL, pageSize, keySearch);
		lstData.forEach(e -> {
			e.setTypeOfFile(getTypeOfFile(e.getPhysicalFileName()));
			e.setPhysicalFileName(e.getPhysicalFileName().replace("//", "/"));
		});
		
		rs.setDatas(lstData);
		rs.setTotalData(lstData.size());
		return rs;
	}
}
