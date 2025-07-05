SELECT
	   m_category.*, 
	   m_parent_category.name parent_name
	FROM
	    m_introduction_category m_category LEFT JOIN m_introduction_category m_parent_category ON (m_category.parent_id= m_parent_category.id) 
	WHERE 
		m_category.id = /*id*/



