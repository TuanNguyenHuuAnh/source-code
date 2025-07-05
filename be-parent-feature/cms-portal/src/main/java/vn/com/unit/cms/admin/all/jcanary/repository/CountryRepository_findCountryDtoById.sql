SELECT
	cou.id as country_id,    
    cou.code as country_code,   
    cou.latitude,
    cou.longtitude,
    cou.web_code,
    cou.phone_code,
    cou.note,
    cou.description,
    cou.create_date,
    cou.active_flag
FROM
	jca_m_country cou			
WHERE
	cou.delete_by IS NULL	
	/*IF countryId != null */
	AND cou.id = /*countryId*/
	/*END*/
	ORDER BY cou.create_date DESC