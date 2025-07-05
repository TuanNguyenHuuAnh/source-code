SELECT
	    id				AS id
	  , m_language_code	AS language_code
	  , title 			AS title
FROM m_customer_type_language 
WHERE
	delete_date is null
	AND m_customer_type_id = /*customerTypeId*/