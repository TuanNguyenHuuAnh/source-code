select ID as user_id
	   , USERNAME as username
	   , EMAIL as email
from JCA_M_ACCOUNT
where 
	DELETED_ID = 0 
	AND ENABLED = 1 
/*IF isPaging == true*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/
		