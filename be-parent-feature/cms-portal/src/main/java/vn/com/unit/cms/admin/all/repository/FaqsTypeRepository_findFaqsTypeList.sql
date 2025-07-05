SELECT
      faqsType.id 				AS  id
	, faqsType.code				AS	code
	, faqsLang.title			AS  title
	, faqsLang.key_word			AS  key_word
	, faqsLang.key_word_description		AS  key_word_description
	, faqsType.status           AS  status
    , faqsType.description		AS	description
  	, faqsType.create_date		AS  create_date
  	, faqsType.create_by        AS  create_by
  	, faqsType.link_alias		AS  link_alias
  	, faqsType.ENABLED			AS  enabled
  	, (select count(1) from m_faqs_category sub 
  	where sub.delete_date is null and sub.delete_by is null
    and sub.M_FAQS_TYPE_ID = faqsType.id) number_category
FROM
		m_faqs_type faqsType
	LEFT JOIN m_faqs_type_language faqsLang ON (faqsLang.m_faqs_type_id = faqsType.id)
	LEFT JOIN m_customer_type_language customerType ON (customerType.m_customer_type_id = faqsType.m_customer_type_id 
											AND customerType.m_language_code = faqsLang.m_language_code
											AND customerType.delete_date is null)
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
	/*IF searchCond.status != null && searchCond.status != ''*/
	AND UPPER(faqsType.status) LIKE ('%'||UPPER(TRIM(/*searchCond.status*/))||'%')
	/*END*/
    /*IF searchCond.enabled != null*/
	AND faqsType.ENABLED = /*searchCond.enabled*/
	/*END*/
    
ORDER BY faqsType.ENABLED DESC, faqsType.sort, faqsType.create_date DESC
OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY