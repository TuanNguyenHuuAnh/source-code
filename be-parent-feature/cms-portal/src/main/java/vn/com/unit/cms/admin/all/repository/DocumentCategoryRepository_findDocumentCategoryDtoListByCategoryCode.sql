SELECT distinct
	m_document_category.id as id,
	m_document_category.code as code,
	m_document_category.parent_id as parentId,
	m_document_category.sort_order as sortOrder,
	m_parent_category.name as parentName	
FROM
	m_document_category m_document_category 
	LEFT JOIN m_document_category m_parent_category ON (m_document_category.parent_id = m_parent_category.id AND m_parent_category.delete_by is NULL)
	    									
	LEFT JOIN m_document_category_language m_language ON (m_language.m_category_id = m_document_category.id AND m_language.delete_by is NULL)
	    									
	LEFT JOIN m_document_type m_type ON (m_type.id = m_document_category.m_document_type_id AND m_type.delete_by is NULL)	    									
WHERE 
	m_document_category.delete_date is NULL
	/*IF condition.customerTypeIdText != null && condition.customerTypeIdText != ''*/
	AND m_document_category.m_customer_type_id = /*condition.customerTypeIdText*/
	/*END*/
	/*IF condition.parentId != null && condition.parentId != ''*/
	AND m_document_category.parent_id = /*condition.parentId*/
	/*END*/
	/*IF condition.typeId != null && condition.typeId != ''*/
	AND m_document_category.m_document_type_id = /*condition.typeId*/
	/*END*/
	/*IF condition.name != null && condition.name != ''*/
	AND UPPER(replace(m_document_category.name,' ','')) like ('%'|| UPPER(replace(/*condition.name*/,' ','')) ||'%')
	/*END*/
	/*IF condition.code != null && condition.code != ''*/
	AND UPPER(replace(m_document_category.code,' ','')) like ('%'|| UPPER(replace(/*condition.code*/,' ','')) ||'%')
	/*END*/	
ORDER BY
	m_document_category.sort_order ASC
