SELECT 
    CONCAT(city.id,'@',city.m_region_id,'@',city.m_country_id) as id,
    lang.name AS name,
    lang.name AS text
FROM jca_m_city city
	left join jca_m_city_language lang ON lang.m_city_id = city.id and lang.delete_date is null
WHERE 
	city.delete_date IS NULL
	AND UPPER(lang.m_language_code) = UPPER(/*languageCode*/'en')
    AND city.m_region_id = /*regionId*/5
	AND city.CTYPE IN /*ctype*/('1','2')
ORDER BY lang.name, city.create_date DESC;