UPDATE jca_account_org
SET POSITION_ID = /*positionId*/
	,POSITION_CODE = null
WHERE POSITION_CODE = /*positionCode*/
	AND COMPANY_ID = /*companyId*/