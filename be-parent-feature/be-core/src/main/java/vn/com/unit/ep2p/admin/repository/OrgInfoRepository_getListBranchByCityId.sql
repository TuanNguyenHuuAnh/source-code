SELECT 
	id,
	org_code,
	org_name,
	org_name_abv,
	city_id
FROM 
	jca_m_org 
WHERE 
	del_flg != 1
	AND deleted_date IS NULL
	AND city_id = /*cityId*/
	AND org_type = 'B'
ORDER BY org_name;