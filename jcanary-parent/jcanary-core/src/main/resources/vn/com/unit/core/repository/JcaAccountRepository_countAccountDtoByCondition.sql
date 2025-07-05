SELECT 
	count(1) 
FROM  
	JCA_ACCOUNT acc
WHERE
	acc.DELETED_ID = 0
	/*BEGIN*/
	AND 
		(
			/*IF jcaAccountSearchDto.username != null && jcaAccountSearchDto.username != ''*/
			OR	UPPER(replace(acc.USERNAME,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaAccountSearchDto.username*/), '%' ))
			/*END*/
			/*IF jcaAccountSearchDto.fullName != null && jcaAccountSearchDto.fullName != ''*/
			OR	UPPER(replace(acc.FULLNAME,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaAccountSearchDto.fullName*/), '%' ))
			/*END*/
			/*IF jcaAccountSearchDto.phone != null && jcaAccountSearchDto.phone != ''*/
			OR	UPPER(replace(acc.PHONE,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaAccountSearchDto.phone*/), '%' ))
			/*END*/
			/*IF jcaAccountSearchDto.email != null && jcaAccountSearchDto.email != ''*/
			OR	UPPER(replace(acc.EMAIL,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaAccountSearchDto.email*/), '%' ))
			/*END*/			
		)
	/*END*/
		
	/*IF jcaAccountSearchDto.actived != null*/
	AND acc.ACTIVED = /*jcaAccountSearchDto.actived*/
	/*END*/	
	/*IF jcaAccountSearchDto.enabled != null*/
	AND acc.ENABLED = /*jcaAccountSearchDto.enabled*/
	/*END*/	
		    
	/*IF jcaAccountSearchDto.companyId != null && jcaAccountSearchDto.companyId != 0*/
	AND acc.COMPANY_ID = /*jcaAccountSearchDto.companyId*/
	/*END*/