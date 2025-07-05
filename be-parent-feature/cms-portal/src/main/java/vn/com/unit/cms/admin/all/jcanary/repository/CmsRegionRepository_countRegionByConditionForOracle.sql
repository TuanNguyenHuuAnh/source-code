SELECT
	count(*)
FROM
    jca_m_region region
    LEFT JOIN jca_m_region_language cl ON region.id = cl.m_region_id    
	LEFT JOIN jca_m_country_language col ON region.m_country_id = col.m_country_id AND cl.m_language_code = col.m_language_code
WHERE
    region.delete_by IS NULL 
     AND cl.m_language_code = UPPER(/*regionSearchDto.languageCode*/)
	/*BEGIN*/
	AND (
	/*IF regionSearchDto.code != null && regionSearchDto.code != ''*/
	OR region.code LIKE concat('%',  /*regionSearchDto.code*/, '%')
	/*END*/
	
	/*IF regionSearchDto.name != null && regionSearchDto.name != ''*/
	OR cl.name LIKE concat('%',  /*regionSearchDto.name*/, '%')
	/*END*/
	
	/*IF regionSearchDto.note != null && regionSearchDto.note != ''*/
	OR region.note LIKE concat('%',  /*regionSearchDto.note*/, '%')
	/*END*/
	
	/*IF regionSearchDto.description != null && regionSearchDto.description != ''*/
	OR region.description LIKE concat('%',  /*regionSearchDto.description*/, '%')
	/*END*/
	
	/*IF regionSearchDto.country != null && regionSearchDto.country != ''*/
	OR col.country_name LIKE concat('%',  /*regionSearchDto.country*/, '%')
	/*END*/	
	
	)
	/*END*/