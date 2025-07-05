SELECT
    doc_category.id AS id,
    doc_category.code AS code,
    doc_category.m_customer_type_id AS str_customer_type_id,
    doc_category_lang.title AS title,
    count_tbl.doc_count AS number_of_document
FROM
    m_document_category doc_category LEFT JOIN m_document_type doc_type ON doc_type.id = doc_category.m_document_type_id
    	LEFT JOIN m_document_category_language doc_category_lang ON doc_category_lang.m_category_id = doc_category.id
    																AND doc_category_lang.m_language_code = /*languageCode*/
    	LEFT JOIN
			(
				SELECT doc.m_document_category_id AS category_id, 
						COUNT(*) AS doc_count
				FROM m_document doc
				WHERE doc.delete_by IS NULL and doc.status = /*processStatus*/
				GROUP BY doc.m_document_category_id

			) count_tbl 
			ON count_tbl.category_id = doc_category.id
WHERE doc_category.delete_by is NULL
	AND doc_type.code = /*typeCode*/
	/*IF customerTypeId != null*/
	AND
	(
		doc_category.m_customer_type_id like CONCAT('%,', /*customerTypeId*/)
		OR
		doc_category.m_customer_type_id like CONCAT(/*customerTypeId*/ , ',%')
		OR
		doc_category.m_customer_type_id like CONCAT('%,', /*customerTypeId*/, ',%')
		OR
		doc_category.m_customer_type_id like /*customerTypeId*/
	)
	/*END*/
ORDER BY
     doc_category.sort_order ASC,doc_category.create_date DESC, doc_category.code ASC