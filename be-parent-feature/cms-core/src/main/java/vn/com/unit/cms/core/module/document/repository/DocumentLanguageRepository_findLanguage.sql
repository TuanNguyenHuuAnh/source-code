SELECT
      *
FROM m_document_language doc_lang
WHERE
    doc_lang.delete_date is null
    AND doc_lang.m_document_id = /*docId*/1
    AND doc_lang.M_LANGUAGE_CODE=/*lang*/'VI'
	