SELECT
	  max(sort)
FROM m_faqs_category
WHERE
	delete_date is null
	AND m_faqs_type_id = /*typeId*/
