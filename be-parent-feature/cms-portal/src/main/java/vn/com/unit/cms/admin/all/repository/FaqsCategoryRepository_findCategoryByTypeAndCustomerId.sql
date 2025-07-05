SELECT
	  categ.id
	, categ.code
	, categLang.title
FROM 
	m_faqs_category categ
LEFT JOIN m_faqs_category_language categLang ON (categLang.m_faqs_category_id = categ.id AND categLang.delete_date is null)
WHERE 
	categ.delete_date is null
	AND categ.ENABLED = 1
	/*IF typeId != null && typeId != ""*/
	AND categ.m_faqs_type_id = /*typeId*/
	/*END*/
	AND categ.m_customer_type_id = /*customerId*/
	AND UPPER(categLang.m_language_code) = UPPER(/*languageCode*/)
ORDER BY categLang.title ASC