/*******************************************************************************
 * Class        RepositoryDto
 * Created date 2018/08/08
 * Lasted date  2018/08/08
 * Author       KhoaNA
 * Change log   2018/08/08 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.dto.AbstractCompanyDto;

@Getter
@Setter
public class RepositoryDto extends AbstractCompanyDto {

	private Long id;

	private String code;

	private String name;

	private String physicalPath;

	private Date durationStart;

	private Date durationEnd;

	private String subFolderRule;

	private String typeRepo;

	private String typeRepoName;

	private Boolean active = false;

	private String description;

	/** for search list */
	private String statusCode;

	/** for search list */
	private Date createdDate;

	private String url;

	private Boolean local = false;

	private String username;

	private String password;
}
