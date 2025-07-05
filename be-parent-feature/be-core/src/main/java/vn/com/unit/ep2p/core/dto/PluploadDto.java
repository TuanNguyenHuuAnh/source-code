package vn.com.unit.ep2p.core.dto;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PluploadDto {

	private String name;
	
	private int chunks = -1;
	
	private int chunk = -1;
	
	private HttpServletRequest request;
	
	private MultipartFile multipartFile;

}
