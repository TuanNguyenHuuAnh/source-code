SELECT * 
from DBO.[FN_GET_STATUS_PROCESS](/*docId*/1723, /*languageCode*/'VI')
WHERE
	STATUS_CODE = /*statusCode*/'999'