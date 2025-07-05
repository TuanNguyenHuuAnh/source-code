package vn.com.unit.ep2p.core.ers.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.ers.annotation.IesTableHeader;

@Getter
@Setter
public class PresentationDto extends ErsAbstract {

	@IesTableHeader(value = "no", width = 50, format = "center")
	private Long no;

	@IesTableHeader(value = "presentation.management.channel")
	private String channelString;
	private ValueAutocompleteDto channel;

	@IesTableHeader(value = "presentation.management.type.link")
	private String typeLinkString;
	private ValueAutocompleteDto typeLink;

	@IesTableHeader(value = "presentation.management.title", width = 300)
	private String title;

	@IesTableHeader(value = "presentation.management.link.url", width = 300)
	private String linkUrl;

	@IesTableHeader(value = "presentation.management.type.open.page")
	private String typeOpenPageString;
	private ValueAutocompleteDto typeOpenPage;

	@IesTableHeader(value = "presentation.management.content", width = 500)
	private String content;

	@IesTableHeader(value = "presentation.management.order.on.page", format = "right")
	private String orderOnPageString;
	private ValueAutocompleteDto orderOnPage;

	@IesTableHeader(value = "presentation.management.status.item.string")
	private String statusItemString;
	private ValueAutocompleteDto statusItem;
	@IesTableHeader("updated.by")
	private String updatedBy;

	@JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
	@IesTableHeader(value = "updated.date", width = 100, format = "date")
	private Date updatedDate;
	
	private Long repoId;

}
