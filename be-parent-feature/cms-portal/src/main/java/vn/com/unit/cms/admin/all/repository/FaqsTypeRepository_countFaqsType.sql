SELECT
    count(*)
FROM
	m_faqs_type faqsType
	LEFT JOIN m_faqs_type_language faqsLang ON (faqsLang.m_faqs_type_id = faqsType.id)
WHERE
	faqsType.delete_date is null
	AND UPPER(faqsLang.m_language_code) = UPPER(/*searchCond.languageCode*/)
	AND faqsType.m_customer_type_id = /*searchCond.customerId*/
	/*IF searchCond.code != null && searchCond.code != ''*/
	AND UPPER(faqsType.code) LIKE ('%'||UPPER(TRIM(/*searchCond.code*/))||'%')
	/*END*/
	
	/*IF searchCond.title != null && searchCond.title != ''*/
    AND UPPER(faqsLang.title) LIKE ('%'||UPPER(TRIM(/*searchCond.title*/))||'%')
    /*END*/
    
	/*IF searchCond.description != null && searchCond.description != ''*/
	AND UPPER(faqsType.description) LIKE ('%'||UPPER(TRIM(/*searchCond.description*/))||'%')
	/*END*/
	
    /*IF searchCond.enabled != null*/
	AND faqsType.ENABLED = /*searchCond.enabled*/
	/*END*/
