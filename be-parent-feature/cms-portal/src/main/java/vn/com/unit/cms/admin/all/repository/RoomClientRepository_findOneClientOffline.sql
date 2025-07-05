select *
from m_room_client_offline tb1
where tb1.created_date is not null
	and tb1.id = /*id*/