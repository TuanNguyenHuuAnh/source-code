// 2021-04-12 LocLT Task #41067

package vn.com.unit.ep2p.ers.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErsResponse<T extends Object> {

	@Getter(value = AccessLevel.NONE)
	private HttpStatus status = HttpStatus.BAD_REQUEST;

	private List<T> data = new ArrayList<>();

	private String msg = "";

	private Long total = 0L;
	private int page = 1;

	public String getStatus() {
		return status.toString();
	}

	public int getStatusCode() {
		return status.value();
	}

}
