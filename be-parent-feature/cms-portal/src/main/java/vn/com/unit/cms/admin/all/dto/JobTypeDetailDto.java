package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("deprecation")
public class JobTypeDetailDto {

	private Long id;
	
	@NotEmpty
	private String code;
	
	@NotNull
	private Long mTypeId;

	@NotNull
	private Long mTypeSubId;
	
	private String note;
	
	private Date createDate;
	
	private String url;

	@Valid
	private List<JobTypeDetailLanguageDto> jtdLanguageDto;
	
	@Valid
	private List<JobTypeLanguageDto> jtLanguageDto;
	
	@Valid
	private List<JobTypeSubLanguageDto> jtsLanguageDto;
	
	/** requestToken */
    private String requestToken;
    
    private String title;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getmTypeId() {
		return mTypeId;
	}

	public void setmTypeId(Long mTypeId) {
		this.mTypeId = mTypeId;
	}

	public Long getmTypeSubId() {
		return mTypeSubId;
	}

	public void setmTypeSubId(Long mTypeSubId) {
		this.mTypeSubId = mTypeSubId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<JobTypeDetailLanguageDto> getJtdLanguageDto() {
		return jtdLanguageDto;
	}

	public void setJtdLanguageDto(List<JobTypeDetailLanguageDto> jtdLanguageDto) {
		this.jtdLanguageDto = jtdLanguageDto;
	}

	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public List<JobTypeLanguageDto> getJtLanguageDto() {
		return jtLanguageDto;
	}

	public void setJtLanguageDto(List<JobTypeLanguageDto> jtLanguageDto) {
		this.jtLanguageDto = jtLanguageDto;
	}

	public List<JobTypeSubLanguageDto> getJtsLanguageDto() {
		return jtsLanguageDto;
	}

	public void setJtsLanguageDto(List<JobTypeSubLanguageDto> jtsLanguageDto) {
		this.jtsLanguageDto = jtsLanguageDto;
	}

	public String getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

    /**
     * Get title
     * @return String
     * @author TranLTH
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title
     * @param   title
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setTitle(String title) {
        this.title = title;
    }
}