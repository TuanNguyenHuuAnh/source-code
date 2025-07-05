package vn.com.unit.process.admin.sla.service.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.process.admin.sla.service.AppSlaInvoledTypeService;
import vn.com.unit.sla.service.impl.SlaInvoledTypeServiceImpl;

@Service
@Primary
@Transactional(readOnly = true)
public class AppSlaInvoledTypeServiceImpl extends SlaInvoledTypeServiceImpl implements AppSlaInvoledTypeService {

}
