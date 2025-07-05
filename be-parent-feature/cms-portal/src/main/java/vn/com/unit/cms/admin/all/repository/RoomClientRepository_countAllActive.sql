select count(tb1.id)
from m_room_client tb1
	left join jca_m_account tb2 on tb1.agent = tb2.username
where tb1.created_date is not null
	and datediff(created_date,now()) = 0
	/*BEGIN*/and (
	
	/*IF roomDto.agent != null && roomDto.agent != ''*/
	or tb2.fullname  like concat('%',  /*roomDto.agent*/, '%')
	/*END*/
	
	)/*END*/