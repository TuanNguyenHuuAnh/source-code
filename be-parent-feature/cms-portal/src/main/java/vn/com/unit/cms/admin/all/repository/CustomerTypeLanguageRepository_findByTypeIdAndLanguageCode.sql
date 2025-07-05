SELECT *
FROM m_customer_type_language
WHERE delete_date is null
	AND m_customer_type_id = /*typeId*/
	AND UPPER(m_language_code) = UPPER(/*languageCode*/)