SELECT
	  *
FROM m_product_category_sub
WHERE
	delete_date is null
	AND m_customer_type_id = /*customerId*/
	AND m_product_category_id = /*productTypeId*/
	ORDER BY SORT ASC
