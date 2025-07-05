SELECT
	   DISTINCT m_document_category.*
	FROM
	    m_document_category m_document_category
	WHERE
		m_document_category.delete_by is NULL
		/*IF customerTypeId != null*/
		and m_document_category.m_customer_type_id = /*customerTypeId*/
		/*END*/
		/*BEGIN*/
		AND
		(
			/*IF typeId != null && typeId != ''*/
			AND m_document_category.m_document_type_id = /*typeId*/
			/*END*/
			/*IF parentId != null && parentId != ''*/
			AND m_document_category.parent_id = /*parentId*/
			/*END*/
		)
		/*END*/
	ORDER BY
		m_document_category.sort_order ASC,
		m_document_category.create_date DESC,
		m_document_category.code ASC




