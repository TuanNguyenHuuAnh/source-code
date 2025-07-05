select CODE	, NAME 
from STG_DMS.DMS_PARTNER  where NVL(EXPIRED_DATE,'9999-12-31') between current date and '9999-12-31'
and 
	/*IF channel != null && channel != ''*/
	CHANNEL = /*channel*/
	/*END*/