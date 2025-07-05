package vn.com.unit.ep2p.core.ers.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValueAutocompleteDto {
	private String title;
	private String value;
	
	// optional
	private String valueName;
	
	public ValueAutocompleteDto(String title, String value) {
		this.title = title;
		this.value = value;
	}
}
