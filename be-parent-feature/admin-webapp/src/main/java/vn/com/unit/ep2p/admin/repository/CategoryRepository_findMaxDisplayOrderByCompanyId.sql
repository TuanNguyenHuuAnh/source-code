SELECT
	CASE WHEN MAX(display_order) IS NOT NULL THEN MAX(display_order) ELSE 0 END AS display_order
FROM 
	EFO_CATEGORY
WHERE 
	company_id = /*companyId*/1
	AND DELETED_ID = 0