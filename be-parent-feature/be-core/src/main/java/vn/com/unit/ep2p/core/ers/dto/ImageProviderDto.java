package vn.com.unit.ep2p.core.ers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageProviderDto {
    
    private String name;

    private String pathFile;
    
    private Float width;
    
    private Float height;
}
