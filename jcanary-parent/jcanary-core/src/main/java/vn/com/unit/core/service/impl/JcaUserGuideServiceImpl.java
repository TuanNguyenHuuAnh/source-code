/*******************************************************************************
 * Class        ：JcaUserGuideServiceImpl
 * Created date ：2021/03/19
 * Lasted date  ：2021/03/19
 * Author       ：tantm
 * Change log   ：2021/03/19：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.entity.JcaUserGuide;
import vn.com.unit.core.repository.JcaUserGuideRepository;
import vn.com.unit.core.service.JcaUserGuideService;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaUserGuideServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaUserGuideServiceImpl implements JcaUserGuideService {

    @Autowired
    private JcaUserGuideRepository jcaUserGuideRepository;

    @Override
    public DbRepository<JcaUserGuide, Long> initRepo() {
        return jcaUserGuideRepository;
    }

}
