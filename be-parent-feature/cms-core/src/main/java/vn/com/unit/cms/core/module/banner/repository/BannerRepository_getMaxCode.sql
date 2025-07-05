select max(CODE) code
from /*$tableName*/
where code LIKE concat('%',/*prefix*/,'%')