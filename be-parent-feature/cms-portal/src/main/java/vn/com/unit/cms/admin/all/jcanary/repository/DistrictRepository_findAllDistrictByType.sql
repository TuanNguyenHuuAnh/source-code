SELECT
	 district.id AS district_id,
     district.code AS district_code,
     dl.name AS district_name,
     district.m_country_id,
     coul.country_name,
     district.m_region_id,
     rl.name AS region_name,
     district.m_city_id,
     cl.name AS city_name,
     district.latitude,
     district.longtitude,
     district.note,
     district.description,
     CONCAT(district.m_city_id,'@',district.m_region_id,'@',district.m_country_id) AS city_region_country,
	 district.DTYPE AS d_type,
	 district.PARENT_DISTRICT_ID AS parent_district_id,
     district.create_date
FROM
	jca_m_district as district
	LEFT JOIN jca_m_district_language dl ON district.id = dl.m_district_id    		
	LEFT JOIN jca_m_city_language cl ON district.m_city_id = cl.m_city_id AND cl.m_language_code = dl.m_language_code
	LEFT JOIN jca_m_country_language AS coul ON district.m_country_id = coul.m_country_id AND dl.m_language_code = coul.m_language_code
	LEFT JOIN jca_m_region_language AS rl ON district.m_region_id = rl.m_region_id AND dl.m_language_code = rl.m_language_code
WHERE 
	district.delete_by IS NULL	
	AND dl.m_language_code = UPPER(/*languageCode*/)
	AND district.DTYPE = /*dType*/