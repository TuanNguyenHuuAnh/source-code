SELECT
	   COUNT(*)
	FROM
		m_math_expression math_expression
	WHERE 
		math_expression.delete_by is NULL
		AND
		math_expression.code = /*code*/

