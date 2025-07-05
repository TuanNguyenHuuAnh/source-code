SELECT
	  *
FROM m_faqs
WHERE
	delete_date is null
	AND ENABLED = 1
	AND m_faqs_category_id =/*categoryId*/
