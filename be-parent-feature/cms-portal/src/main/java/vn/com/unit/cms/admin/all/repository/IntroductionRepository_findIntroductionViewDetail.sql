SELECT
	   m_intro.*, 
	   m_category.name category_name
	FROM
	    m_introduction m_intro LEFT JOIN m_introduction_category m_category ON (m_intro.m_introduction_category_id = m_category.id) 
	WHERE 
		m_intro.id = /*id*/



