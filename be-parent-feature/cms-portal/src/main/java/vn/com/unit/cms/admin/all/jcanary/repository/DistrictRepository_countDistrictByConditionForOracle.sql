SELECT
	count(*)
FROM
    jca_m_district district    
    LEFT JOIN jca_m_district_language dl ON district.id = dl.m_district_id    
	LEFT JOIN jca_m_city_language cl ON district.m_city_id = cl.m_city_id AND cl.m_language_code = dl.m_language_code
	LEFT JOIN jca_m_country_language coul ON district.m_country_id = coul.m_country_id AND dl.m_language_code = coul.m_language_code
	LEFT JOIN jca_m_region_language rl ON district.m_region_id = rl.m_region_id AND dl.m_language_code = rl.m_language_code
WHERE
    district.delete_by IS NULL 	
    AND dl.m_language_code = UPPER(/*districtSearchDto.languageCode*/)
	/*BEGIN*/
	AND (
	/*IF districtSearchDto.code != null && districtSearchDto.code != ''*/
	OR UPPER(district.code) LIKE UPPER('%' || TRIM(/*districtSearchDto.code*/) || '%')
	/*END*/
	/*IF districtSearchDto.name != null && districtSearchDto.name != ''*/
	OR UPPER(dl.name) LIKE UPPER('%' ||  TRIM(/*districtSearchDto.name*/) || '%')
	/*END*/
	/*IF districtSearchDto.note != null && districtSearchDto.note != ''*/
	OR UPPER(district.note) LIKE UPPER('%' ||  TRIM(/*districtSearchDto.note*/) || '%')
	/*END*/
	/*IF districtSearchDto.country != null && districtSearchDto.country != ''*/
	OR UPPER(coul.country_name) LIKE UPPER('%' ||  TRIM(/*districtSearchDto.country*/) || '%')
	/*END*/	
	/*IF districtSearchDto.region != null && districtSearchDto.region != ''*/
	OR UPPER(rl.name) LIKE UPPER('%' ||  TRIM(/*districtSearchDto.region*/) || '%')
	/*END*/
	/*IF districtSearchDto.city != null && districtSearchDto.city != ''*/
	OR UPPER(cl.name) LIKE UPPER('%' ||  TRIM(/*districtSearchDto.city*/) || '%')
	/*END*/
	)
	/*END*/