select cast(tb1.id as VARCHAR2(40)) as id_type,tb2.title as text, 1 is_product
from m_product_category tb1,m_product_category_language tb2
where tb1.delete_date is null 
and tb1.id = tb2.m_product_category_id
and UPPER(tb2.m_language_code) = UPPER(/*lang*/)
and tb1.m_customer_type_id = /*id*/