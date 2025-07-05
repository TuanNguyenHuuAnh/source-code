
SELECT
	math_expression.id									AS	id
	,math_expression.expression							AS	expression
	,math_expression.m_customer_type_id					AS	m_customer_type_id
	,math_expression.expression_type					AS	expression_type
	,math_expression.m_customer_type_id 				AS customer_type_id
	,math_expression.sub_type							AS 	sub_type
	,customer_type.name 					AS  customer_type_name
FROM m_math_expression math_expression
	LEFT JOIN m_customer_type customer_type ON math_expression.m_customer_type_id = customer_type.id
WHERE
	math_expression.delete_date is NULL
	AND
	math_expression.expression_type = /*expressionType*/