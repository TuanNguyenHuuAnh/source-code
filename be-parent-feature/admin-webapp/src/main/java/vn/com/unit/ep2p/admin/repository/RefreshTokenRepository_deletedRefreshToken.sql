DELETE
from JCA_API_REFRESH_TOKEN refresh
where
refresh.ACCOUNT_ID = /*accountId*/
/*IF refreshToken != null && refreshToken != ''*/
	AND refresh.REFRESH_TOKEN = /*refreshToken*/ 
/*END*/