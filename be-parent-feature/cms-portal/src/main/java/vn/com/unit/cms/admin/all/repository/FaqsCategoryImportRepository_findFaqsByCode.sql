SELECT
    *
FROM
    m_faqs faqs 
WHERE
    faqs.delete_date is null
    AND faqs.code = /*faqsCode*/'FAQ2110.0003'