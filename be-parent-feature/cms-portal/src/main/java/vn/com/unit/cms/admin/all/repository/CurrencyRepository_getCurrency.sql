
SELECT
	id		
	, code 
	, name  	
	, m_language_code
FROM
	 m_currency
WHERE
	delete_date is NULL
	AND
	id = /*id*/
	AND 
	UPPER(m_language_code) =  UPPER(/*language*/)
