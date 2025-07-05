SELECT 
	* 
FROM 
	jca_m_org 
WHERE 
	deleted_date IS NULL
ORDER BY 
	org_level, parent_org_id, order_by;