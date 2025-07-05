package vn.com.unit.cms.core.module.notify.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NotifyInputDto {
	private String notifyTitlte;
	private String content;
	private String linkNotify;
	private boolean isSendImmediately;
	private Date sendDate;
	private boolean isActive;
	List<NotifyAddDto> lstData;
}
