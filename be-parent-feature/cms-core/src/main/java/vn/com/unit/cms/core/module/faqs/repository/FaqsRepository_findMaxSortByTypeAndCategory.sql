SELECT
  max(sort)
FROM m_faqs
WHERE
	delete_date is null
	AND m_faqs_type_id = /*typeId*/
	/*IF categoryId != null*/
	AND m_faqs_category_id = /*categoryId*/
	/*END*/
	/*IF categoryId == null*/
	AND m_faqs_category_id is null
	/*END*/

