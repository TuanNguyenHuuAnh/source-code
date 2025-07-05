SELECT *
FROM m_faqs_language
WHERE delete_date is null
    AND m_faqs_id = /*faqsId*/'13'
    AND m_language_code = /*lang*/'VI'