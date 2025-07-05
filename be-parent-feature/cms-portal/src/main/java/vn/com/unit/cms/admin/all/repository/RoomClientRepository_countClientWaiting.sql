select count(*)
from m_room_client tb1
where tb1.status = 2
	/*IF isRoleAdmin == 0*/
		and tb1.agent = /*username*/
	/*END*/