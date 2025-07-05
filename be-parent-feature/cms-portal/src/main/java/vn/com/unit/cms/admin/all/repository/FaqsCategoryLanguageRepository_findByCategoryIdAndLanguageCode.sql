SELECT *
FROM m_faqs_category_language
WHERE
	delete_date is null
	AND m_faqs_category_id = /*categoryId*/
	AND UPPER(m_language_code) = UPPER(/*languageCode*/)