package vn.com.unit.cms.core.module.information.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GaInforRes {
    private GAInformationDto mainGa;
    private List<GAInformationDto> listSubGa;
}
