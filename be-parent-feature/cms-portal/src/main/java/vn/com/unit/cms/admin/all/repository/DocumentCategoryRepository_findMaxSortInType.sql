SELECT 
	max(m_category.sort_order)
FROM
    m_document_category m_category
    /*IF typeId != null && typeId != ''*/
WHERE 
	m_category.m_document_type_id = /*typeId*/
	AND
	m_category.delete_by IS NULL
	/*END*/
    /*IF typeId == null || typeId == ''*/
WHERE 
	m_category.m_document_type_id IS NULL
	AND
	m_category.delete_by IS NULL
	/*END*/

