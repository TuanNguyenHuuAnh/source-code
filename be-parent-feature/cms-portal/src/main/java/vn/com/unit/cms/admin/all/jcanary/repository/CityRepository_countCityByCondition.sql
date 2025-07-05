SELECT
	count(*)
FROM
    jca_m_city city
    LEFT JOIN jca_m_city_language cl ON city.id = cl.m_city_id    
	LEFT JOIN jca_m_country_language col ON city.m_country_id = col.m_country_id AND cl.m_language_code = col.m_language_code
	LEFT JOIN jca_m_region_language rl ON city.m_region_id = rl.m_region_id AND cl.m_language_code = rl.m_language_code
WHERE
    city.delete_by IS NULL 	
    AND cl.m_language_code = UPPER(/*citySearchDto.languageCode*/)
	/*BEGIN*/
	AND (
	/*IF citySearchDto.code != null && citySearchDto.code != ''*/
	OR city.code LIKE concat('%',  /*citySearchDto.code*/, '%')
	/*END*/
	
	/*IF citySearchDto.name != null && citySearchDto.name != ''*/
	OR cl.name LIKE concat('%',  /*citySearchDto.name*/, '%')
	/*END*/
	
	/*IF citySearchDto.note != null && citySearchDto.note != ''*/
	OR city.note LIKE concat('%',  /*citySearchDto.note*/, '%')
	/*END*/
	
	/*IF citySearchDto.description != null && citySearchDto.description != ''*/
	OR city.description LIKE concat('%',  /*citySearchDto.description*/, '%')
	/*END*/
	
	/*IF citySearchDto.country != null && citySearchDto.country != ''*/
	OR col.country_name LIKE concat('%',  /*citySearchDto.country*/, '%')
	/*END*/	
	
	/*IF citySearchDto.region != null && citySearchDto.region != ''*/
	OR rl.name LIKE concat('%',  /*citySearchDto.region*/, '%')
	/*END*/
	)
	/*END*/