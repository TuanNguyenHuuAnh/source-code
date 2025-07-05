SELECT
      faqsType.id 				AS  id
	, faqsType.code				AS	code
	, faqsLang.title			AS  title
    , faqsType.description		AS	description
  	, faqsType.create_date		AS  create_date
  	, faqsType.link_alias		AS  link_alias
FROM
	m_faqs_type faqsType
	LEFT JOIN m_faqs_type_language faqsLang ON (faqsLang.m_faqs_type_id = faqsType.id)
	LEFT JOIN m_customer_type_language customerType ON (customerType.m_customer_type_id = faqsType.m_customer_type_id 
											AND customerType.m_language_code = faqsLang.m_language_code
											AND customerType.delete_date is null)
WHERE
	faqsType.delete_date is null
	AND faqsType.ENABLED = 1
	AND UPPER(faqsLang.m_language_code) = UPPER(/*languageCode*/)
	AND faqsType.m_customer_type_id = /*customerId*/
ORDER BY faqsType.sort;
