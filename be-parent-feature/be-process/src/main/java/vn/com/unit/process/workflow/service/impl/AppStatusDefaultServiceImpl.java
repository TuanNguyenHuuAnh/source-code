/*******************************************************************************
 * Class        ：AppStatusDefaultServiceImpl
 * Created date ：2019/08/28
 * Lasted date  ：2019/08/28
 * Author       ：KhuongTH
 * Change log   ：2019/08/28：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.process.workflow.repository.AppStatusSystemDefaultRepository;
import vn.com.unit.process.workflow.service.AppStatusDefaultService;
import vn.com.unit.workflow.entity.JpmStatusDefault;


/**
 * AppStatusDefaultServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppStatusDefaultServiceImpl implements AppStatusDefaultService {

    @Autowired
    private AppStatusSystemDefaultRepository appStatusSystemDefaultRepository;
    
    /**
     * @author KhuongTH
     */
    @Override
    public List<JpmStatusDefault> getListDefault() {
        return (List<JpmStatusDefault>) appStatusSystemDefaultRepository.findAll();
    }

}
