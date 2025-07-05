UPDATE JCA_M_ACCOUNT
SET 
	/*IF failedLoginCount != null*/
		FAILED_LOGIN_COUNT = /*failedLoginCount*/
	/*END*/
	/*IF loginDate != null*/
		,LOGIN_DATE = /*loginDate*/
	/*END*/
WHERE ID = /*accountId*/