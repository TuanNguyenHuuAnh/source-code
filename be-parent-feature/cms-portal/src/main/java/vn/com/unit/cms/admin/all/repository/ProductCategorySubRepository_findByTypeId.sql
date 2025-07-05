SELECT
	  *
FROM m_product_category_sub
WHERE
	delete_date is null
	AND m_customer_type_id = /*typeId*/
	ORDER BY SORT ASC
