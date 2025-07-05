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
     district.create_date
FROM
	jca_m_district district
	LEFT JOIN jca_m_language lang ON UPPER(TRIM(lang.code)) = UPPER(TRIM(/*languageCode*/''))
	LEFT JOIN jca_m_district_language dl ON district.id = dl.m_district_id AND dl.m_language_code = lang.code
	LEFT JOIN jca_m_city_language cl ON district.m_city_id = cl.m_city_id AND cl.m_language_code = lang.code
	LEFT JOIN jca_m_country_language coul ON district.m_country_id = coul.m_country_id AND lang.code = coul.m_language_code
	LEFT JOIN jca_m_region_language rl ON district.m_region_id = rl.m_region_id AND lang.code = rl.m_language_code
WHERE 
	district.delete_by IS NULL	
	/*IF districtDto.mCityId != null*/
	AND district.m_city_id = /*districtDto.mCityId*/
	/*END*/
	/*IF districtDto.mRegionId != null*/
	AND district.m_region_id = /*districtDto.mRegionId*/
	/*END*/
	/*IF districtDto.mCountryId != null*/
	AND district.m_country_id = /*districtDto.mCountryId*/
	/*END*/
	/*IF districtDto.districtCode != null && districtDto.districtCode != ''*/
	AND district.code = /*districtDto.districtCode*/
	/*END*/
	ORDER BY NLSSORT(dl.name, 'NLS_SORT=generic_m'), district.create_date DESC