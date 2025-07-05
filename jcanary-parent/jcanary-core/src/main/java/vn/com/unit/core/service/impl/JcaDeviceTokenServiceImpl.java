/*******************************************************************************
 * Class        ：JcaDeviceTokenServiceImpl
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.repository.JcaDeviceTokenRepository;
import vn.com.unit.core.service.JcaDeviceTokenService;
import vn.com.unit.dts.exception.DetailException;


/**
 * <p>
 * JcaDeviceTokenServiceImpl
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaDeviceTokenServiceImpl implements JcaDeviceTokenService{
    
    /** The jca device token repository. */
    @Autowired
    private JcaDeviceTokenRepository jcaDeviceTokenRepository;

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.JcaDeviceTokenService#getDeviceTokenByAccountId(java.lang.Long)
     */
    @Override
    public List<String> getDeviceTokenByAccountId(Long accountId) throws DetailException {
        
        if (null == accountId) {
            throw new DetailException("ACCOUNT ID NULL");
        } else {
            List<String> result = jcaDeviceTokenRepository.getListTokenByAccountId(accountId);
            if (null == result) {
                result = new ArrayList<>();
            }
            return result;
        }
        
    }

    @Override
    public List<String> getDeviceTokenByListAccountId(List<Long> accountIds) throws DetailException {
        List<String> result = new ArrayList<>();
        if (null != accountIds) {
            int size =  accountIds.size();
            int step = size / 1000;
            
            for (int i = 0; i <= step; i++) {
                List<Long> stepAccountIds = new ArrayList<>();
                int start = i * 1000;
                int end = (i * 1000) + 1000;
                if (size < end) {
                    end = (i * 1000) + (size % 1000);
                }
                for (int j = start; j < end; j++) {
                    stepAccountIds.add(accountIds.get(j));
                }
                List<String> stepDeviceToken = jcaDeviceTokenRepository.getListTokenByListAccountId(stepAccountIds);
                result.addAll(stepDeviceToken);
            }
        }
        return result;
    }

}
