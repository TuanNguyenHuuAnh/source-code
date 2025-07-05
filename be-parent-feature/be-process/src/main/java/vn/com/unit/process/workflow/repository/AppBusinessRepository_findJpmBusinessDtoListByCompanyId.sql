SELECT 
	business.ID 				as ID
	, business.BUSINESS_CODE 	as CODE
	, business.BUSINESS_NAME 	as NAME
FROM 
	JPM_BUSINESS business
WHERE
	business.DELETED_ID = 0 
	/*IF companyId != null*/
	AND ( COMPANY_ID = /*companyId*/1
		  OR COMPANY_ID IS NULL ) 
	/*END*/
	/*IF companyId == null*/
	AND COMPANY_ID IS NULL 
	/*END*/
ORDER BY
	business.BUSINESS_NAME 
	
