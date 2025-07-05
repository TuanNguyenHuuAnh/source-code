package vn.com.unit.process.admin.sla.service.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.process.admin.sla.service.AppSlaConfigService;
import vn.com.unit.sla.service.impl.SlaConfigServiceImpl;

@Service
@Primary
@Transactional(readOnly = true)
public class AppSlaConfigServiceImpl extends SlaConfigServiceImpl implements AppSlaConfigService {

}
