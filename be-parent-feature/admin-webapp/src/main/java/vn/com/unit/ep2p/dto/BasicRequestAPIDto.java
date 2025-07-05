package vn.com.unit.ep2p.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicRequestAPIDto {
	int count;
	List<Object> datas;
}
