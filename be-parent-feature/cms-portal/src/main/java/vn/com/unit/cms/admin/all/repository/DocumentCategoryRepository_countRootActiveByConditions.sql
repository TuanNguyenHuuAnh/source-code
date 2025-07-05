SELECT
	   COUNT(DISTINCT m_document_category.id)
	FROM
	    m_document_category m_document_category LEFT JOIN m_document_category m_parent_category ON (m_document_category.parent_id = m_parent_category.id AND m_parent_category.delete_by is NULL)
	    											LEFT JOIN m_document_category_language m_language ON (
	    											m_language.m_category_id = m_document_category.id
	    											AND m_language.m_language_code = /*languageCode*/
	    											AND m_language.delete_by is NULL)
	    											LEFT JOIN m_document_type m_type ON (m_type.id = m_document_category.m_document_type_id AND m_type.delete_by is NULL)
	    											/*IF condition.customerType != null && condition.customerType != ''*/
	    											LEFT JOIN m_customer_type m_customer ON (
	    												m_document_category.m_customer_type_id like CONCAT('%,', m_customer.id)
														OR
														m_document_category.m_customer_type_id like CONCAT(m_customer.id , ',%')
														OR
														m_document_category.m_customer_type_id like CONCAT('%,',m_customer.id, ',%')
													)
													/*END*/
	    											
	WHERE 
		m_document_category.delete_by is NULL
		AND
		m_document_category.parent_id is NULL
		/*IF condition.customerTypeIdText != null && condition.customerTypeIdText != ''*/
		AND m_document_category.m_customer_type_id = /*condition.customerTypeIdText*/
		/*END*/
	/*BEGIN*/
		AND
		(
			/*IF condition.customerType != null && condition.customerType != ''*/
			OR
			replace(m_customer.name,' ','') like CONCAT('%',/*condition.customerType*/,'%')
			OR
			replace(m_customer.code,' ','') like CONCAT('%',/*condition.customerType*/,'%')
			/*END*/
			/*IF condition.name != null && condition.name != ''*/
			OR
			replace(m_document_category.name,' ','') like CONCAT('%',/*condition.name*/,'%')
			/*END*/
			/*IF condition.code != null && condition.code != ''*/
			OR
			replace(m_document_category.code,' ','') like CONCAT('%',/*condition.code*/,'%')
			/*END*/
			/*IF condition.note != null && condition.note != ''*/
			OR
			replace(m_document_category.note,' ','') like CONCAT('%',/*condition.note*/,'%')
			/*END*/
			/*IF condition.description != null && condition.description != ''*/
			OR
			replace(m_document_category.description,' ','') like CONCAT('%',/*condition.description*/,'%')
			/*END*/
			/*IF condition.sortOrder != null && condition.sortOrder != ''*/
			OR
			replace(m_document_category.sort_order,' ','') like CONCAT('%',/*condition.sortOrder*/,'%')
			/*END*/
			/*IF condition.title != null && condition.title != ''*/
			OR
			replace(m_language.title,' ','') like CONCAT('%',/*condition.title*/,'%')
			/*END*/
			/*IF condition.typeName != null && condition.typeName != ''*/
			OR
			replace(m_type.name,' ','') like CONCAT('%',/*condition.typeName*/,'%')
			/*END*/
			/*IF condition.parentName != null && condition.parentName != ''*/
			OR
			replace(m_parent_category.name,' ','') like CONCAT('%',/*condition.parentName*/,'%')
			/*END*/
		)
	/*END*/



