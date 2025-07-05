SELECT 
	* 
FROM 
	jca_m_org 
WHERE 
	deleted_date IS NULL
	AND (org_type = 'S' OR org_type = 'B')
ORDER BY 
	org_level, parent_org_id, order_by;