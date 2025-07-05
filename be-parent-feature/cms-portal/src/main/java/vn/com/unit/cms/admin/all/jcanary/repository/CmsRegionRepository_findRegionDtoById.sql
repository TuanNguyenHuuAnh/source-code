SELECT
	region.id as region_id,    
    region.code as region_code, 
    region.m_country_id,
    region.note,
    region.description,
    region.create_date,
    region.active_flag
FROM
	jca_m_region region			
WHERE
	region.delete_by IS NULL	
	/*IF regionId != null */
	AND region.id = /*regionId*/
	/*END*/
	ORDER BY region.create_date DESC