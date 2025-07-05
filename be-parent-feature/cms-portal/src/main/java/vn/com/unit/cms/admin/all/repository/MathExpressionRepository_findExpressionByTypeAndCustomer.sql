
SELECT
	id									AS	id
	,expression							AS	expression
	,m_customer_type_id					AS	m_customer_type_id
	,expression_type					AS	expression_type
	,sub_type							AS 	sub_type
FROM m_math_expression math_expression

WHERE
	math_expression.delete_date is NULL
	AND
	math_expression.m_customer_type_id = /*customerTypeId*/
	AND
	math_expression.expression_type = /*expressionType*/