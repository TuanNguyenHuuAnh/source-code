--
-- FaqsTypeLanguageRepository_findByTypeId.sql

SELECT *
FROM m_faqs_type_language
WHERE delete_date is null
	AND m_faqs_type_id = /*typeId*/