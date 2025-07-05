SELECT
    type_lang.introduce    
FROM
    m_document_type doc_type LEFT JOIN m_document_type_language type_lang ON type_lang.m_document_type_id = doc_type.id
    AND
    type_lang.m_language_code = /*languageCode*/
WHERE
	doc_type.code = /*typeCode*/
