SELECT
	   COUNT(DISTINCT math_expression.id)
	FROM
		m_math_expression math_expression
		LEFT JOIN m_customer_type customer_type ON math_expression.m_customer_type_id = customer_type.id
	WHERE 
		math_expression.delete_by is NULL
		and math_expression.m_customer_type_id = /*searchDto.customerTypeId*/9
		/*IF searchDto.name != null && searchDto.name != ''*/
		AND math_expression.name like concat('%',/*searchDto.name*/,'%')
		/*END*/
		/*IF searchDto.code != null && searchDto.code != ''*/
		and math_expression.code like concat('%',/*searchDto.code*/,'%')
		/*END*/
		/*IF searchDto.status != null && searchDto.status != ''*/
		AND math_expression.status like concat('%',/*searchDto.status*/,'%')
		/*END*/
		/*IF searchDto.typeText != null && searchDto.typeText != ''*/
		AND math_expression.expression_type like concat('%',/*searchDto.typeText*/,'%')
		/*END*/
	