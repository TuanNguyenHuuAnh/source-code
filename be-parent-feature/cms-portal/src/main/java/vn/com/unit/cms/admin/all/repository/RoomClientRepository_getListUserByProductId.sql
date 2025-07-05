select username
from m_role_for_chat
where delete_date is null
and m_product_type_id in /*list*/()
group by username