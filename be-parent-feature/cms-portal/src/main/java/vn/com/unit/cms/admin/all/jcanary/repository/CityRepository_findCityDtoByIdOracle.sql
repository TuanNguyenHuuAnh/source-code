SELECT
	 id as city_id,
     code as city_code,     
	 m_region_id,
	 m_country_id,
	 latitude,
	 longtitude,
	 note,
	 description,
	 CONCAT(m_country_id,'@',m_region_id) AS region_country,
	 create_date,
	 ctype AS c_type,
	 CONCAT(parent_city_id,'@',m_region_id,'@',m_country_id) AS parent_city,
	 phone_code,
	 zip_code,
	 ship_code,
	 car_code,
	 active_flag
FROM
	jca_m_city
WHERE 
	delete_by IS NULL
	/*IF cityId != null*/
	AND id = /*cityId*/
	/*END*/
	ORDER BY create_date DESC