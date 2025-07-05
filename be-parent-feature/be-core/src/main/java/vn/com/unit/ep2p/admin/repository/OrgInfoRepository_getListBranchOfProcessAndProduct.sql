SELECT 
	oi.id,
	oi.org_code,
	oi.org_name
FROM 
	product_process AS pp 
	LEFT JOIN jca_m_org AS oi ON pp.branch_id = oi.id
WHERE 
	pp.del_flg = '0'
	AND oi.del_flg = '0'
    AND pp.product_id = /*productId*/
    AND pp.process_id = /*processId*/ 