SELECT
	  *
FROM M_FAQS_TYPE
WHERE
	delete_date is null
	AND ENABLED = 1
	AND m_customer_type_id = /*typeId*/
	ORDER BY SORT ASC