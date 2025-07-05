package vn.com.unit.ep2p.admin.repository;
import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.core.entity.JcaMenuLang;
import vn.com.unit.core.repository.JcaMenuLangRepository;

public interface MenuLanguageRepository extends JcaMenuLangRepository{
    /**
     * findMenuIdLanguage
     *
     * @param menuId
     * @return List<MenuLanguage>
     * @author TranLTH
     */
	public List<JcaMenuLang> findListMenuLanguageId(@Param("menuId")Long menuId);
	
	/*-----------------------BEGIN_ORACLE----------------------*/
	// MenuLanguageRepository_findListMenuLanguageIdOracle
	public List<JcaMenuLang> findListMenuLanguageIdOracle(@Param("menuId") Long menuId);
	/*-----------------------END_ORACLE----------------------*/
	
	
}
