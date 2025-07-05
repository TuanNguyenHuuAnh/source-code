SELECT
    customer_type.name
FROM
    m_customer_type customer_type
WHERE
	customer_type.delete_date IS NULL
	AND
	customer_type.id IN /*typeIds*/()
