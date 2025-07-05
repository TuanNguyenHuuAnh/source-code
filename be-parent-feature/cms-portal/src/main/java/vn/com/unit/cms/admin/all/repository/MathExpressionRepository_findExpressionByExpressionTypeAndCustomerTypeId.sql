SELECT
	math_expression.id							AS	id
	,math_expression.name 						AS  name
FROM m_math_expression math_expression
WHERE
	math_expression.delete_date is NULL
	
	/*IF expressionType != null && expressionType != ''*/
	AND math_expression.expression_type = /*expressionType*/
	/*END*/
	AND math_expression.M_CUSTOMER_TYPE_ID = /*customerTypeId*/
	/*IF status != null*/
	AND math_expression.status = /*status*/
	/*END*/
order by math_expression.name asc