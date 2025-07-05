SELECT
	    m_introduction_category.id AS id,
	    m_introduction_category.code AS code,
	    m_introduction_category.name AS name,   
	    m_introduction_category.create_date AS create_date
	FROM
	    m_introduction_category
	WHERE 
		delete_by is NULL
		AND
		parent_id is NULL
		AND
		enabled = 1
ORDER BY
create_date DESC, code ASC
