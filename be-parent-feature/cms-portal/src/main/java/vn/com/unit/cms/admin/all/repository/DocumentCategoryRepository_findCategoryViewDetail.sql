SELECT
	   m_category.*, 
	   m_parent_category.name parent_name,
	   m_document_type.name document_type_name
	FROM
	    m_document_category m_category LEFT JOIN m_document_category m_parent_category ON (m_category.parent_id= m_parent_category.id)
	    									LEFT JOIN m_document_type m_document_type ON (m_category.m_document_type_id = m_document_type.id)
	WHERE 
		m_category.id = /*id*/



