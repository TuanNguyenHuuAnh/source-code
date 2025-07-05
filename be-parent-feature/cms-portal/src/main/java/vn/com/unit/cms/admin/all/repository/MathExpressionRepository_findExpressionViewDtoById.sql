
SELECT distinct
	math_expression.*
	,math_expression.m_customer_type_id AS str_customer_type_id
	,customer_type.name 					AS  customer_type_name
FROM m_math_expression math_expression
	LEFT JOIN m_customer_type customer_type ON INSTR(math_expression.m_customer_type_id, TO_CHAR(customer_type.id), 1) > 0
WHERE
	math_expression.delete_date is NULL
	AND
	math_expression.id = /*id*/