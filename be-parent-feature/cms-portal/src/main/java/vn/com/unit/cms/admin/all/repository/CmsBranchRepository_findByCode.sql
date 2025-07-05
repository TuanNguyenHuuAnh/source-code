SELECT
    *    
FROM
    jca_m_branch
WHERE
	code = /*code*/
	AND delete_date IS NULL
ORDER BY create_date DESC