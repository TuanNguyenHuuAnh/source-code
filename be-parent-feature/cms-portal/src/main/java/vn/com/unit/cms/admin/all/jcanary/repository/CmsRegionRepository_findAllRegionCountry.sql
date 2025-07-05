
SELECT
	CONCAT(region.m_country_id,'@',region.id) AS id,
	CONCAT(cl.name,' - ',col.country_name) AS text,
	CONCAT(cl.name,' - ',col.country_name) AS name
FROM
	jca_m_region region	
	LEFT JOIN jca_m_language lang ON UPPER(lang.code) = UPPER(/*language*/)
	LEFT JOIN jca_m_region_language cl ON region.id = cl.m_region_id AND cl.m_language_code = lang.code
	LEFT JOIN jca_m_country_language col ON region.m_country_id = col.m_country_id AND lang.code = col.m_language_code
WHERE
	region.delete_by IS NULL	
	ORDER BY region.create_date DESC
	

	
	
	