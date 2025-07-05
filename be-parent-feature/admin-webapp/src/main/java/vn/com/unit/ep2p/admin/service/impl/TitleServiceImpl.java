/*******************************************************************************
 * Class        ：TitleServiceImpl
 * Created date ：2020/03/17
 * Lasted date  ：2020/03/17
 * Author       ：KhuongTH
 * Change log   ：2020/03/17：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.ep2p.admin.repository.TitleRepository;
import vn.com.unit.ep2p.admin.service.TitleService;


/**
 * TitleServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class TitleServiceImpl implements TitleService {

    @Autowired
    private TitleRepository titleRepository;
    
    @Override
    public String getHighestTitleByListTitleCode(List<String> titleCodes, Long companyId) {
        return titleRepository.getHighestTitleByListTitleCode(titleCodes, companyId);
    }

}
