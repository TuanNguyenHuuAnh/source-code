/*
 * 
 */
package vn.com.unit.ep2p.admin.service;

import java.util.List;

import vn.com.unit.core.entity.JcaMenuLang;
import vn.com.unit.core.service.JcaMenuLangService;

/**
 * <p>
 * MenuLangService
 * </p>
 * .
 *
 * @author tantm
 * @version 01-00
 * @since 01-00
 */
public interface MenuLangService extends JcaMenuLangService {

    /**
     * <p>
     * Find list menu language id.
     * </p>
     *
     * @author tantm
     * @param menuId
     *            type {@link Long}
     * @return {@link List<JcaMenuLang>}
     */
    List<JcaMenuLang> findListMenuLanguageId(Long menuId);

}