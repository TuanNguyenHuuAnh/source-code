SELECT
	cou.id as country_id,    
    cou.code as country_code,
    cl.country_name,
    cl.local_name,
    cou.latitude,
    cou.longtitude,
    cou.web_code,
    cou.phone_code,
    cou.note,
    cou.description,
    cou.create_date
FROM
	jca_m_country cou	
	LEFT JOIN jca_m_country_language cl ON cou.id = cl.m_country_id 	
WHERE
	cou.delete_by IS NULL	
	AND cl.m_language_code = UPPER(/*countrySearchDto.languageCode*/)
	/*BEGIN*/
	AND (
	/*IF countrySearchDto.code != null && countrySearchDto.code != ''*/
	OR cou.code LIKE concat('%',  /*countrySearchDto.code*/, '%')
	/*END*/
	/*IF countrySearchDto.name != null && countrySearchDto.name != ''*/
	OR cl.country_name LIKE concat('%',  /*countrySearchDto.name*/, '%')
	/*END*/
	/*IF countrySearchDto.description != null && countrySearchDto.description != ''*/
	OR cou.description LIKE concat('%',  /*countrySearchDto.description*/, '%')
	/*END*/
	/*IF countrySearchDto.webCode != null && countrySearchDto.webCode != ''*/
	OR cou.web_code LIKE concat('%',  /*countrySearchDto.webCode*/, '%')
	/*END*/
	/*IF countrySearchDto.phoneCode != null && countrySearchDto.phoneCode != ''*/
	OR cou.phone_code LIKE concat('%',  /*countrySearchDto.phoneCode*/, '%')
	/*END*/
	
	)
	/*END*/
	ORDER BY cou.create_date DESC
	LIMIT  /*sizeOfPage*/ OFFSET  /*offset*/   
	


	
	
