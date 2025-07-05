select max(CODE) code
from M_ORDER
where CODE LIKE concat('%',/*prefix*/,'%')