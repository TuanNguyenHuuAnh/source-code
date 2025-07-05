package vn.com.unit.ep2p.core.sla.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.service.JcaDeviceTokenService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.noti.service.SlaDeviceTokenService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaSlaDeviceTokenServiceImpl implements SlaDeviceTokenService {
    
    @Autowired
    private JcaDeviceTokenService jcaDeviceTokenService;

    @Override
    public List<String> getListTokenByListReceiverIdList(List<Long> receiverIdList) {
        List<String> result = new ArrayList<>();
        try {
            result = jcaDeviceTokenService.getDeviceTokenByListAccountId(receiverIdList);
        } catch (DetailException e) {
            e.printStackTrace();
        }
        return result;
    }

}
