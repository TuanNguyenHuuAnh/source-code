DELETE FROM 
	JCA_DEVICE_TOKEN device 
WHERE
	device.DEVICE_TOKEN = /*deviceToken*/ 
	/*IF accountId!=null*/
	AND device.ACCOUNT_ID = /*accountId*/
	/*END*/