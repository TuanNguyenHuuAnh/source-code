SELECT
	    m_document_category.id AS id,
	    m_document_category.code AS code,
	    m_document_category.name AS name   
	FROM
	    m_document_category
	WHERE 
		delete_by is NULL
		AND
		parent_id is NULL
		/*IF customerTypeId != null*/
		and m_customer_type_id = /*customerTypeId*/
		/*END*/
ORDER BY
code ASC
