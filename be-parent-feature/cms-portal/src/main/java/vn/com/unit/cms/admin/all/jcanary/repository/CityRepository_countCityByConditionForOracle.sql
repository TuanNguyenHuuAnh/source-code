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
	OR UPPER(city.code) LIKE UPPER(('%' ||  trim(/*citySearchDto.code*/) || '%'))
	/*END*/
	
	/*IF citySearchDto.name != null && citySearchDto.name != ''*/
	OR UPPER(cl.name) LIKE UPPER(('%' ||  trim(/*citySearchDto.name*/) || '%'))
	/*END*/
	
	/*IF citySearchDto.note != null && citySearchDto.note != ''*/
	OR UPPER(city.note) LIKE UPPER(('%' ||  trim(/*citySearchDto.note*/) || '%'))
	/*END*/
	
	/*IF citySearchDto.description != null && citySearchDto.description != ''*/
	OR UPPER(city.description) LIKE UPPER(('%' ||  trim(/*citySearchDto.description*/) || '%'))
	/*END*/
	
	/*IF citySearchDto.country != null && citySearchDto.country != ''*/
	OR UPPER(col.country_name) LIKE UPPER(('%' ||  trim(/*citySearchDto.country*/) || '%'))
	/*END*/	
	
	/*IF citySearchDto.region != null && citySearchDto.region != ''*/
	OR UPPER(rl.name) LIKE UPPER(('%' ||  trim(/*citySearchDto.region*/) || '%'))
	/*END*/
	)
	/*END*/