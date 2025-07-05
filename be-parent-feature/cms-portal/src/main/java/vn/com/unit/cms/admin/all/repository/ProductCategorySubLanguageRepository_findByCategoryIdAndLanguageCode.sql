SELECT *
FROM m_product_category_sub_language
WHERE
	delete_date is null
	AND m_product_category_sub_id = /*categorySubId*/
	AND UPPER(m_language_code) = UPPER(/*languageCode*/)