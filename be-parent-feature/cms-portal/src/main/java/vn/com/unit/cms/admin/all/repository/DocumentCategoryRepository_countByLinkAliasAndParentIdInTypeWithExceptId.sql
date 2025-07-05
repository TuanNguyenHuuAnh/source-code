SELECT
	   COUNT(DISTINCT m_document_category.id)
	FROM
	    m_document_category m_document_category LEFT JOIN m_document_type m_type ON (m_type.id = m_document_category.m_document_type_id AND m_type.delete_by is NULL)
	WHERE 
		m_document_category.delete_by is NULL
		/*IF linkAlias != null && linkAlias != ''*/
		AND
		m_document_category.link_alias = /*linkAlias*/
		/*END*/
		/*IF exceptId != null && exceptId != ''*/
		AND
		m_document_category.id != /*exceptId*/
		/*END*/
		/*IF parentId != null && parentId != ''*/
		AND
		m_document_category.parent_id = /*parentId*/
		/*END*/
		/*IF typeId != null && typeId != ''*/
		AND
		m_document_category.m_document_type_id = /*typeId*/
		/*END*/
		




