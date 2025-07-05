SELECT ID FROM jca_team 
WHERE 
	DELETED_ID = 0 
	AND CODE IN /*codes*/() 
	/*IF companyId != null*/ 
	AND(COMPANY_ID = /*companyId*/ OR COMPANY_ID IS NULL) 
	/*END*/