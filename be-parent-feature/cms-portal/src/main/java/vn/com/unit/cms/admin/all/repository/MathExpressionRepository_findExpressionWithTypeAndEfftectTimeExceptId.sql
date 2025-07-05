SELECT
	math_expression.*
	,math_expression.m_customer_type_id AS str_customer_type_id
FROM m_math_expression math_expression
WHERE
	math_expression.delete_date is NULL
	/*IF cusTypeIdText != null && cusTypeIdText != ''*/
	AND math_expression.m_customer_type_id = /*cusTypeIdText*/
	/*END*/
	/*IF exceptId != null*/
	AND
	math_expression.id != /*exceptId*/
	/*END*/
	AND(
		math_expression.available_date_from >= /*availableDateFrom*/
		AND
		math_expression.available_date_from < /*availableDateTo*/
		OR
		math_expression.available_date_to <= /*availableDateTo*/
		AND
		math_expression.available_date_to > /*availableDateFrom*/
		OR
		math_expression.available_date_to >= /*availableDateTo*/
		AND
		math_expression.available_date_from <= /*availableDateFrom*/
	)
	AND
	math_expression.expression_type = /*expressionType*/