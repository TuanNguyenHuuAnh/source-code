select count(product_chat.m_product_type_id)
from
(	
	select tb1.m_product_type_id
	from m_role_for_chat tb1, 
	     m_product_category_language tb2, 
	     jca_m_account tb3
	where tb1.m_product_type_id = tb2.m_product_category_id
	and tb1.delete_date is null
	and tb1.username = tb3.username 
	and UPPER(tb2.m_language_code) = UPPER(/*lang*/)
	/*BEGIN*/ AND (
		/*IF userDto.product != null && userDto.product != ''*/
		OR tb2.title LIKE ('%'|| /*userDto.product*/ ||'%')
		/*END*/
		/*IF userDto.user != null && userDto.user != ''*/
		OR tb3.fullname LIKE ('%'|| /*userDto.user*/ ||'%')
		/*END*/
		/*IF userDto.user != null && userDto.user != ''*/
		OR tb3.username LIKE ('%'|| /*userDto.user*/ ||'%')
		/*END*/
	) /*END*/
	group by tb1.m_product_type_id
)product_chat, 
(
	select tb1.id as id,tb2.title 
	from m_customer_type tb1, m_customer_type_language tb2
	where tb1.delete_date is null
	and tb1.id = tb2.m_customer_type_id
	and UPPER(tb2.m_language_code) = UPPER(/*lang*/)
)customer_type,
(
	select tb1.id,tb1.m_customer_type_id,tb2.title as product_category
	from m_product_category tb1,m_product_category_language tb2
	where tb1.delete_date is null 
	and tb1.id = tb2.m_product_category_id
	and UPPER(tb2.m_language_code) = UPPER(/*lang*/)
)category
where  product_chat.m_product_type_id = category.id    
and customer_type.id = category.m_customer_type_id