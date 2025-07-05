package vn.com.unit.cms.core.module.income.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YearlyPaymentDto {
    private List<MonthPaymentDto> monthPayment;
    private List<TotalPaymentDto> totalPayment;
    
}
