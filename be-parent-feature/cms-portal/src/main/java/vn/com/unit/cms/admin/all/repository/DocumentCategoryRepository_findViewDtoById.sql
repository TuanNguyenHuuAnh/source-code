SELECT
    doc_category.id AS id,
    doc_category.code AS code,
    doc_category_lang.title AS title
FROM
    m_document_category doc_category 
    	LEFT JOIN m_document_category_language doc_category_lang ON doc_category_lang.m_category_id = doc_category.id
    																AND doc_category_lang.m_language_code = /*languageCode*/
WHERE doc_category.delete_by is NULL
	AND doc_category.id = /*id*/