package vn.com.unit.process.admin.sla.service.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.process.admin.sla.service.AppSlaConfigAlertToService;
import vn.com.unit.sla.service.impl.SlaConfigAlertToServiceImpl;

@Service
@Primary
@Transactional(readOnly = true)
public class AppSlaConfigAlertToServiceImpl extends SlaConfigAlertToServiceImpl implements AppSlaConfigAlertToService{

}
