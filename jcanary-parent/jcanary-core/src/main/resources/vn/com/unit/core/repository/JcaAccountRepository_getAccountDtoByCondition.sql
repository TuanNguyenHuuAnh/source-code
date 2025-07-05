-- 
-- JcaAccountRepository_getAccountDtoByCondition.sql
SELECT 
	  acc.ID 						as USER_ID
	  ,acc.CODE 					as CODE
	  ,acc.FULLNAME 				as FULLNAME
	  ,acc.USERNAME 				as USERNAME
	  ,acc.EMAIL 					as EMAIL
	  ,acc.RECEIVED_NOTIFICATION 	as RECEIVED_NOTIFICATION
	  ,acc.RECEIVED_EMAIL 			as RECEIVED_EMAIL
	  ,acc.PHONE 					as PHONE
	  ,acc.GENDER 					as GENDER
	  ,acc.BIRTHDAY 				as BIRTHDAY
	  ,acc.ACTIVED 					as ACTIVED
	  ,acc.ENABLED 					as ENABLED
	  ,acc.AVATAR 					as AVATAR
	  ,acc.AVATAR_REPO_ID 			as AVATAR_REPO_ID
	  ,com.NAME 					as COMPANY_NAME
	  ,pos.NAME 					as POSITION_NAME
FROM 
	JCA_ACCOUNT acc
LEFT JOIN
	JCA_POSITION pos
ON	
	pos.ID = acc.POSITION_ID 
LEFT JOIN
  	JCA_COMPANY com
ON
  	com.ID = acc.COMPANY_ID
	
WHERE
	acc.DELETED_ID = 0

	AND UPPER(acc.USERNAME) <> UPPER('candidate') -- prevent mistake delete candidate user

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
/*IF orders != null*/
ORDER BY /*$orders*/username
-- ELSE ORDER BY ISNULL(acc.UPDATED_DATE, acc.CREATED_DATE) DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/