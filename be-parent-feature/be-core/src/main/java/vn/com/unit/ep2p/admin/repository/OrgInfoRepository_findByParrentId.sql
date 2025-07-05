SELECT 
	* 
FROM 
	jca_m_org 
WHERE 
	deleted_date IS NULL
	AND parent_org_id = /*parrentOrgId*/