package vn.com.unit.ep2p.admin.service;

import java.util.List;

import vn.com.unit.ep2p.admin.dto.IncomeDto;

public interface NotifyIncomeService {
    
    public Long pushListNotifyForWeb(List<IncomeDto> incomeInfo);
}
