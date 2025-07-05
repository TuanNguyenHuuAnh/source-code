/*******************************************************************************
 * Class        MenuValidator
 * Created date 2017/04/17
 * Lasted date  2017/04/17
 * Author       TranLTH
 * Change log   2017/04/1701-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.core.entity.JcaMenu;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.MenuDto;
import vn.com.unit.ep2p.admin.service.MenuService;

/**
 * MenuValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Component
public class MenuValidator implements Validator {
    /** MenuService */
    @Autowired
    MenuService menuService;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return MenuDto.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        MenuDto menuDto = (MenuDto) target;        
        if (null == menuDto.getMenuId()){
            checkDuplicateCode(menuDto, errors);
        }  else{
            checkDeletedMenuId(menuDto, errors);
        }
    }

    /**
     * checkDuplicateCode
     *
     * @param menuDto
     * @param errors
     * @author TranLTH
     */
    private void checkDuplicateCode(MenuDto menuDto, Errors errors) {
        String menuCode = menuDto.getMenuCode();
        Long companyId = menuDto.getCompanyId();
        JcaMenu checkMenu = menuService.findMenuByCodeAndCompanyId(menuCode, companyId);
        if (null != checkMenu) {
            String checkMenuCode = checkMenu.getCode();
            if (menuCode.equals(checkMenuCode)) {
                String[] errorArgs = new String[1];
                errorArgs[0] = menuCode;
                errors.rejectValue("menuCode", "message.error.menu.code.existed", errorArgs, ConstantCore.EMPTY);
            }
        }
    }
    
    /**
     * checkDeletedMenuId
     *
     * @param menuDto
     * @param errors
     * @author TranLTH
     */
    private void checkDeletedMenuId(MenuDto menuDto, Errors errors){
    	JcaMenu listMenu = menuService.findMenu(menuDto.getMenuId());
        if (null == listMenu){
            String[] errorArgs = new String[1];
            errorArgs[0] = menuDto.getMenuId() + "";
            errors.rejectValue("menuId", "message.error.menu.id.delete", errorArgs, ConstantCore.EMPTY);
        }
    }
}
