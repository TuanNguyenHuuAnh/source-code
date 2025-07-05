
SELECT
	*
FROM m_math_expression math_expression

WHERE
	math_expression.delete_date is NULL
	AND
	math_expression.m_customer_type_id = /*customerTypeId*/
	AND
	math_expression.expression_type = /*expressionType*/
	AND
	math_expression.sub_type = /*subType*/