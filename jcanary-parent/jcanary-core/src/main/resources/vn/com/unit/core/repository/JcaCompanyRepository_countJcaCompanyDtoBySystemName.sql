SELECT
	  COUNT(1)
FROM 
	JCA_COMPANY com
WHERE  
	com.DELETED_ID = 0
	AND com.SYSTEM_NAME = /*systemName*/

	/*IF companyId != null */
	AND com.ID  != /*companyId*/
	/*END*/	
