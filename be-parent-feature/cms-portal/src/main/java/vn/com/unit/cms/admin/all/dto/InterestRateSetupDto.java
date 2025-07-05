
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

import vn.com.unit.core.dto.LanguageDto;

public class InterestRateSetupDto {

    private LanguageDto languageDto;

    private List<InterestRateTitleDto> lstTitleDto;

    private List<InterestRateValueDto> lstValueDto;

    public LanguageDto getLanguageDto() {
        return languageDto;
    }

    public void setLanguageDto(LanguageDto languageDto) {
        this.languageDto = languageDto;
    }

    public List<InterestRateTitleDto> getLstTitleDto() {
        return lstTitleDto;
    }

    public void setLstTitleDto(List<InterestRateTitleDto> lstTitleDto) {
        this.lstTitleDto = lstTitleDto;
    }

    public List<InterestRateValueDto> getLstValueDto() {
        return lstValueDto;
    }

    public void setLstValueDto(List<InterestRateValueDto> lstValueDto) {
        this.lstValueDto = lstValueDto;
    }

}
