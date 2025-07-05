SELECT
	  max(sort)
FROM m_product_category_sub
WHERE
	delete_date is null
	AND m_customer_type_id = /*typeId*/
	/*IF categoryId != null*/
	AND m_product_category_id = /*categoryId*/
	/*END*/
	/*IF categoryId == null*/
	AND m_product_category_id is null
	/*END*/
