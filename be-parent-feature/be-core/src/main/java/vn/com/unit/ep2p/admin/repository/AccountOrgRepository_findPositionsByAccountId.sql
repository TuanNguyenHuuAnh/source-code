SELECT POSITION_ID FROM jca_account_org 
WHERE 
	DELETED_ID = 0 
	AND ASSIGN_TYPE = 1 
	AND ACCOUNT_ID = /*accountId*/1700 
	AND EFFECTED_DATE <= /*startDate*/'' 
	AND EXPIRED_DATE >= /*endDate*/''