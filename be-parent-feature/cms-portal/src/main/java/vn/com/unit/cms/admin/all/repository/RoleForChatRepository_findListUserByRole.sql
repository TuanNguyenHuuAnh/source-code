select username
from m_role_for_chat
where delete_date is null
and role_code in /*role*/
group by username