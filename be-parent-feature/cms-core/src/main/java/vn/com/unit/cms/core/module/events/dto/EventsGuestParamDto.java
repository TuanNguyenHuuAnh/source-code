package vn.com.unit.cms.core.module.events.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class EventsGuestParamDto {
    @In
    public String eventId;
    @In
    public Integer page;
    @In
    public Integer pageSize;
	@In
	public String sort;
	@In
	public String search;

    @ResultSet
    public List<EventsGuestDetailDto> lstData;
    @Out
    public Integer totalRows;
}
