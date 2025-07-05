SELECT
    type_lang.title 
FROM
    m_customer_type customer_type LEFT JOIN m_customer_type_language type_lang ON customer_type.id = type_lang.m_customer_type_id
    AND
    type_lang.m_language_code = /*languageCode*/
WHERE
	customer_type.delete_date IS NULL
	AND
	type_lang.m_language_code = /*languageCode*/
	AND
	customer_type.id = /*typeId*/
