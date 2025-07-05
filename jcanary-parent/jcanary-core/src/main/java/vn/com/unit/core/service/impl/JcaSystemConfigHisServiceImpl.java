/*******************************************************************************
 * Class        ：JcaSystemConfigHisServiceImpl
 * Created date ：2021/03/03
 * Lasted date  ：2021/03/03
 * Author       ：ngannh
 * Change log   ：2021/03/03：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.unit.core.entity.JcaSystemConfigHis;
import vn.com.unit.core.repository.JcaSystemConfigHisRepository;
import vn.com.unit.core.service.JcaSystemConfigHisService;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaSystemConfigHisServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */


@Service
public class JcaSystemConfigHisServiceImpl implements JcaSystemConfigHisService{
    
    @Autowired
    JcaSystemConfigHisRepository jcaSystemConfigHisRepository;
    /* (non-Javadoc)
     * @see vn.com.unit.db.service.DbRepositoryService#initRepo()
     */
    @Override
    public DbRepository<JcaSystemConfigHis, Long> initRepo() {
        // TODO Auto-generated method stub
        return jcaSystemConfigHisRepository;
    }

}
