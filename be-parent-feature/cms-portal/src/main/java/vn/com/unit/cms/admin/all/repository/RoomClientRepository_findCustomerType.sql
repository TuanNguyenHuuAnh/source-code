select cast(tb1.id AS varchar2(30)) as id_type, tb2.title as text,0 is_product
from m_customer_type tb1, m_customer_type_language tb2
where tb1.delete_date is null
and tb1.id = tb2.m_customer_type_id
and UPPER(tb2.m_language_code) = UPPER(/*lang*/)