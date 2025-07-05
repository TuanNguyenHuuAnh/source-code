select max(NOTIFY_CODE) code
from M_NOTIFYS
where NOTIFY_CODE LIKE concat('%',/*prefix*/,'%')