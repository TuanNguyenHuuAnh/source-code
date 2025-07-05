SELECT
	 id AS district_id,
	 code AS district_code,
	 m_country_id,
	 m_region_id,
	 m_city_id,
	 latitude,
	 longtitude,
	 note,
	 description,	 
	 CONCAT(m_city_id,'@',m_region_id,'@',m_country_id) AS city_region_country,
	 create_date,
	 dtype AS d_type,
	 CONCAT(parent_district_id,'@',m_city_id,'@',m_region_id,'@',m_country_id) AS parent_district,
	 active_flag
FROM
	jca_m_district 	
WHERE 
	delete_by IS NULL
	/*IF districtId != null*/
	AND id = /*districtId*/
	/*END*/	
	ORDER BY create_date DESC