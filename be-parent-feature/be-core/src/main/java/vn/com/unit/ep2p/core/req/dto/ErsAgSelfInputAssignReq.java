package vn.com.unit.ep2p.core.req.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.ep2p.core.ers.dto.ValueAutocompleteDto;

@Getter
@Setter
@NoArgsConstructor
public class ErsAgSelfInputAssignReq {

	private ValueAutocompleteDto assignee;
	
	private List<Long> listId;
	
}
