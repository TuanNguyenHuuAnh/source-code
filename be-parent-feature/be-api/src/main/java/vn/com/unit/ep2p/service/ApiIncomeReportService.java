package vn.com.unit.ep2p.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.module.income.dto.IncomePersonalMonthlyDto;
import vn.com.unit.cms.core.module.income.dto.IncomePersonalMonthSearchDto;
import vn.com.unit.cms.core.module.income.dto.IncomePersonalWeeklyDto;
import vn.com.unit.cms.core.module.income.dto.IncomePersonalYearlyDto;
import vn.com.unit.cms.core.module.income.dto.IncomePersonalYearlySearchDto;
import vn.com.unit.cms.core.module.income.dto.YearlyPaymentDto;
import vn.com.unit.cms.core.module.report.dto.IncomeBonusReportMO;
import vn.com.unit.cms.core.module.report.dto.IncomeBonusReportSearchDto;
import vn.com.unit.cms.core.module.report.dto.IncomeBonusReportTQP;
import vn.com.unit.cms.core.module.report.dto.IncomeBonusReportTSM;
import vn.com.unit.core.res.ObjectDataRes;

public interface ApiIncomeReportService {
    public List<IncomeBonusReportMO> getListReportIncomeBonusMO(IncomeBonusReportSearchDto searchDto);

    public List<IncomeBonusReportTQP> getListReportIncomeBonusTQP(IncomeBonusReportSearchDto searchDto);

    public List<IncomeBonusReportTSM> getListReportIncomeBonusTSM(IncomeBonusReportSearchDto searchDto);

    @SuppressWarnings("rawtypes")
    ResponseEntity exportReportIncome(IncomeBonusReportSearchDto searchDto, Locale locale);

    // monthly
    public List<IncomePersonalMonthlyDto> getListIncomePersonalMonthlyDetail(IncomePersonalMonthSearchDto searchDto);
    
    public List<IncomePersonalMonthlyDto> getListIncomePersonalMonthlySumary(IncomePersonalMonthSearchDto searchDto);

    @SuppressWarnings("rawtypes")
    ResponseEntity exportIncomePersonalMonthly(IncomePersonalMonthSearchDto searchDto);

    // weekly
    public ObjectDataRes<IncomePersonalWeeklyDto> getListIncomePersonalWeekly(IncomePersonalMonthSearchDto searchDto);

    @SuppressWarnings("rawtypes")
    ResponseEntity exportIncomePersonalWeekly(IncomePersonalMonthSearchDto searchDto);

    // yearly
    public List<IncomePersonalYearlyDto> getListIncomePersonalYearly(IncomePersonalYearlySearchDto searchDto);

    @SuppressWarnings("rawtypes")
    ResponseEntity exportIncomePersonalYearly(IncomePersonalYearlySearchDto searchDto);

    public void setDataHeaderToXSSFWorkbookSheet(XSSFWorkbook xssfWorkbook, int sheetNumber, String[] titleHeader, String titleName, String startRow, List<String> datas);

    @SuppressWarnings("rawtypes")
    public<T, E extends Enum<E>, M> ResponseEntity exportListData(List<T> resultDto,String type,BigDecimal amount,  String fileName, String row, Class<E> enumDto, Class<M> className, List<String> lstInfor, String startInfo);

    // bang luong nam AG
    public YearlyPaymentDto getYearlyPaymentForAG(IncomePersonalYearlySearchDto searchDto);
    @SuppressWarnings("rawtypes")
    ResponseEntity exportYearlyPaymentForAG(IncomePersonalYearlySearchDto searchDto);

    // bang luong nam GA
    public YearlyPaymentDto getYearlyPaymentForGA(IncomePersonalYearlySearchDto searchDto);
    @SuppressWarnings("rawtypes")
    ResponseEntity exportYearlyPaymentForGA(IncomePersonalYearlySearchDto searchDto);
}
