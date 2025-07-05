SELECT
	ntype.ID 					AS 	id,
   	typeLang.LABEL 				AS 	name,
   	ntype.TYPE_OF_LIBRARY 		AS 	type_of_library
FROM m_news_type ntype
JOIN m_news_type_language typeLang ON (ntype.id = typeLang.m_news_type_id AND typeLang.delete_date is null)
LEFT JOIN m_customer_type_language customerType ON (customerType.m_customer_type_id = ntype.m_customer_type_id 
											AND customerType.m_language_code = typeLang.m_language_code
											AND customerType.delete_date is null)
WHERE
	ntype.delete_date is null
	AND UPPER(typeLang.m_language_code) = UPPER(/*languageCode*/)
	AND ntype.m_customer_type_id = /*customerTypeId*/
	AND ntype.status = /*status*/ 
  	AND ntype.enabled = 1
	AND ntype.TYPE_OF_LIBRARY != 0