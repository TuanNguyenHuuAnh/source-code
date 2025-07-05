
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

public class InterestRateTitleDto {

    private Long id;

    private String interestRateType;

    private String languageCode;

    private String title;

    private Long customerTypeId;

    private List<InterestRateTitleDto> datas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInterestRateType() {
        return interestRateType;
    }

    public void setInterestRateType(String interestRateType) {
        this.interestRateType = interestRateType;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<InterestRateTitleDto> getDatas() {
        return datas;
    }

    public void setDatas(List<InterestRateTitleDto> datas) {
        this.datas = datas;
    }

    public Long getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

}
