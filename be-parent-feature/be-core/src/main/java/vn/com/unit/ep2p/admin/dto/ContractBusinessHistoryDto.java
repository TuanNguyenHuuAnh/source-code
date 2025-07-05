package vn.com.unit.ep2p.admin.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ContractBusinessHistoryDto {

    private String status; // Tinh trang

	private String requestType; // Loai yeu cau
	
	private Date requestDate; //Ngay thuc hien yeu cau

}