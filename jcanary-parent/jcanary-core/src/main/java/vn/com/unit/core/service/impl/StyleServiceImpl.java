/*******************************************************************************
 * Class        :StyleServiceImpl
 * Created date :2019/06/10
 * Lasted date  :2019/06/10
 * Author       :HungHT
 * Change log   :2019/06/10:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.dto.JcaStyleDto;
import vn.com.unit.core.repository.StyleRepository;
import vn.com.unit.core.service.StyleService;



/**
 * StyleServiceImpl
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class StyleServiceImpl implements StyleService {  

    @Autowired
    StyleRepository styleRepository;


    @Override
    public List<JcaStyleDto> getStyleList() {
        return styleRepository.getStyleList();
    }
}