SELECT *
FROM
    JCA_EMAIL_TEMPLATE 
where 
	DELETED_ID = 0
	and code = /*code*/
	/*IF companyId != null*/
	and (COMPANY_ID = /*companyId*/
		or COMPANY_ID is null)
	/*END*/