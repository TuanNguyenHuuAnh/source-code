select max(EVENT_CODE) code
from M_EVENTS
where EVENT_CODE LIKE concat('%',/*prefix*/,'%')