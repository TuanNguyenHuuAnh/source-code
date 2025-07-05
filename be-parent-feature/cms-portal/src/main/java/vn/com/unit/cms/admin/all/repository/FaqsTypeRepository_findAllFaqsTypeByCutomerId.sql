SELECT
    typeCate.id 		AS id,
    typeCate.code		AS code,
    typeCateLang.title  AS title,
    typeCate.link_alias AS link_alias
FROM
	m_faqs_type typeCate
LEFT JOIN m_faqs_type_language typeCateLang 
	ON (typeCateLang.m_faqs_type_id = typeCate.id 
		AND typeCateLang.delete_date is null)
	
WHERE
	typeCate.delete_date is null
	AND typeCate.enabled = 1
	AND typeCateLang.m_language_code = UPPER(/*lang*/'vi')
	/*IF customerTypeId != null*/
    AND typeCate.M_CUSTOMER_TYPE_ID = /*customerTypeId*/
    /*END*/
ORDER BY typeCateLang.title ASC, typeCate.create_date
