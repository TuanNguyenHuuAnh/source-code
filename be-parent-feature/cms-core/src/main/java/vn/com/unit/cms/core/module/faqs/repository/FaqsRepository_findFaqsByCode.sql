SELECT
    *
FROM
	m_faqs faqs
WHERE
	faqs.delete_date is null
    AND faqs.ENABLED = 1
    AND faqs.code = /*faqsCode*/
    