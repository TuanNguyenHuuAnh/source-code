SELECT 
    district.id || '@' || district.m_city_id || '@' || district.m_region_id || '@'|| district.m_country_id as id,
    lang.name AS name,
    lang.name AS text
FROM jca_m_district district
	left join jca_m_district_language lang ON lang.m_district_id = district.id and lang.delete_date is null
WHERE 
	district.delete_date IS NULL
	AND UPPER(lang.m_language_code) = UPPER(/*languageCode*/'')
	/*IF cityId != null*/
    	AND district.m_city_id = /*cityId*/''
    /*END*/
ORDER BY lang.name, district.create_date DESC;