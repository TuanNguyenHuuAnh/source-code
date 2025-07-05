select max(CODE) code
from /*$tableName*/ with(nolock)
where code LIKE concat('%',/*prefix*/,'%')