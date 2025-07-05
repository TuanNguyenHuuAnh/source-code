SELECT
	CONCAT(city.id,'@',city.m_region_id,'@',city.m_country_id) AS id,
	CONCAT(cil.name,' - ',cl.name,' - ',col.country_name) AS text,
	CONCAT(cil.name,' - ',cl.name,' - ',col.country_name) AS name
FROM
	jca_m_city as city
	LEFT JOIN jca_m_language lang ON lang.code = /*language*/'en'
	LEFT JOIN jca_m_city_language cil ON city.id = cil.m_city_id AND cil.m_language_code = lang.code
	LEFT JOIN jca_m_region_language cl ON city.m_region_id = cl.m_region_id AND cl.m_language_code = lang.code
	LEFT JOIN jca_m_country_language col ON city.m_country_id = col.m_country_id AND lang.code = col.m_language_code
	
WHERE city.DELETE_DATE IS NULL

ORDER BY city.create_date DESC