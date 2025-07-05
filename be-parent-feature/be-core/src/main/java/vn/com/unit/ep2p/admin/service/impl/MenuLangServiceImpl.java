package vn.com.unit.ep2p.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.core.entity.JcaMenuLang;
import vn.com.unit.core.service.impl.JcaMenuLangServiceImpl;
import vn.com.unit.ep2p.admin.repository.MenuLanguageRepository;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.MenuLangService;

@Service
@Primary
@Transactional
public class MenuLangServiceImpl extends JcaMenuLangServiceImpl implements MenuLangService, AbstractCommonService {

    @Autowired
    private JCommonService commonService;

    @Autowired
    private MenuLanguageRepository menuLanguageRepository;

    @Override
    public JCommonService getCommonService() {
        return commonService;
    }

    @Override
    public List<JcaMenuLang> findListMenuLanguageId(Long menuId) {
        return menuLanguageRepository.findListMenuLanguageId(menuId);
    }
}
