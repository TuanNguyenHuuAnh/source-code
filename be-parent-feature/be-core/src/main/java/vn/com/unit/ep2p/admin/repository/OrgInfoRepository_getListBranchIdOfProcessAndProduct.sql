SELECT 
	pp.product_process_id	AS product_process_id,
	pp.branch_id			AS branch_id,
	org.org_name_abv 		AS branch_name,
	pp.effective_date 		AS effective_date,
	org.org_code			AS branch_code
FROM 
	product_process AS pp 
	LEFT JOIN jca_m_org AS org ON org.id = pp.branch_id 
WHERE 
    pp.product_id = /*productId*/
    AND pp.process_id = /*processId*/ 