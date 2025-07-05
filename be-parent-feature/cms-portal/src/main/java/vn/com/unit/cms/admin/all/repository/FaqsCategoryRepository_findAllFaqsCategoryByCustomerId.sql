SELECT
    cate.id 							AS id,
    cate.code,
    cateLang.title 						AS title
FROM
	m_faqs_category cate
	LEFT JOIN m_faqs_category_language cateLang 
	ON (cateLang.m_faqs_category_id = cate.id AND cateLang.m_language_code = UPPER(/*lang*/) AND  cateLang.delete_date is null)
WHERE
	cate.delete_date is null
	AND cate.ENABLED = 1
	/*IF customerId != null*/
	AND cate.m_customer_type_id = /*customerId*/
	/*END*/