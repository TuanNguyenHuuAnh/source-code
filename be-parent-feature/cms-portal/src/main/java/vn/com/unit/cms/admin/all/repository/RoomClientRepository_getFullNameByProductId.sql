select tb2.fullname
from m_role_for_chat tb1,
     jca_m_account tb2
where tb1.m_product_type_id = /*id*/
and tb1.username = tb2.username 
and tb1.delete_date is null