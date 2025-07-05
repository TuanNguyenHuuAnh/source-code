SELECT
	  max(sort)
FROM m_product_category
WHERE
	delete_date is null
	AND m_customer_type_id = /*typeId*/
