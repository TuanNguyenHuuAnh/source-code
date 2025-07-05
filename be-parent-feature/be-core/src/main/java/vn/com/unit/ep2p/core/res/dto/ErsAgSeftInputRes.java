package vn.com.unit.ep2p.core.res.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.ep2p.core.ers.dto.ValueAutocompleteDto;
import vn.com.unit.ep2p.core.ers.entity.AbstractTracking;

@Getter
@Setter
@NoArgsConstructor
public class ErsAgSeftInputRes extends AbstractTracking {
	
	private Long id;
	
	private String channel;
	
	@NotNull
	private ValueAutocompleteDto applyForPosition; 
	
	@NotNull
	@NotBlank
	@Size(min=0, max= 255)
	private String candidateName;

	@NotNull
	@NotBlank
	private String gender;

	private String genderName; 

	@NotNull
	private Date dob;

	@NotNull
	@NotBlank
	private String idNo;
	
	@NotNull
	@NotBlank
	@Size(min=0, max= 255)
	private String email;

	@NotNull
	@NotBlank
	private String mobile;

	@NotNull
	@NotBlank
	@Size(min=0, max= 255)
	private String currentAddress;

	@Size(min=0, max= 255)
	private String currentNest;

	@NotNull
	private ValueAutocompleteDto currentProvince;
	
	@NotNull
	private ValueAutocompleteDto currentDistrict;
	
	@NotNull
	private ValueAutocompleteDto currentWard; 

	private Long adCode;
	
	private String adName;
	
	private Date allocationAdDate; 
	
	private Long recruiterCode; 
	
	private String recruiterName;
	
	private Date allocationRecruiterDate;
	
	private Integer arAllocationName;
	
	private String statusForm; 
	
	@NotNull
	private Boolean agree; 
}
