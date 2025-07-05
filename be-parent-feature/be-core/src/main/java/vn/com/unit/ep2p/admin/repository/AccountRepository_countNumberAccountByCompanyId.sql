SELECT 
	count(*)
FROM jca_account 
WHERE DELETED_ID = 0
/*IF companyId != null*/
AND COMPANY_ID = /*companyId*/1
/*END*/