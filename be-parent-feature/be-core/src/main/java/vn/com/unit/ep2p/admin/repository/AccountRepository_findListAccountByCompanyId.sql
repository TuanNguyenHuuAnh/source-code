select ID as user_id
	   , USERNAME as username
	   , EMAIL as email
from JCA_M_ACCOUNT
where 
	DELETED_ID = 0 
	AND ENABLED = 1 
	/*IF id != null*/
	AND COMPANY_ID = /*id*/1
	/*END*/
		