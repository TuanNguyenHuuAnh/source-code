SELECT 
	email.ID 				AS ID
	, email.TO_STRING		AS TO_STRING
	, email.CC_STRING 		AS CC_STRING
	, email.BCC_STRING		AS BCC_STRING
	, email.SUBJECT			AS SUBJECT
	, email.EMAIL_CONTENT 	AS EMAIL_CONTENT
	, email.SEND_DATE		AS SEND_DATE
	, email.SEND_STATUS 	AS SEND_STATUS
	, email.COMPANY_ID		AS COMPANY_ID
	, email.DEPARTMENT_ID 	AS DEPARTMENT_ID
FROM 
	JCA_EMAIL email
WHERE 
	email.DELETED_ID = 0
	/*IF searchDto.companyId != null*/
		AND email.COMPANY_ID = /*searchDto.companyId*/
	/*END*/
	/*BEGIN*/
		AND (
		/*IF searchDto.receiveAddress != null && searchDto.receiveAddress != ''*/
			OR UPPER(email.TO_STRING) LIKE concat('%',  concat(UPPER(/*searchDto.receiveAddress*/), '%'))
		/*END*/
		/*IF searchDto.senderAddress != null && searchDto.senderAddress != ''*/
			OR UPPER(email.SENDER_ADDRESS) LIKE concat('%',  concat(UPPER(/*searchDto.senderAddress*/), '%'))
		/*END*/
		/*IF searchDto.subject != null && searchDto.subject != ''*/
			OR UPPER(email.SUBJECT) LIKE concat('%',  concat(UPPER(/*searchDto.subject*/), '%'))
		/*END*/
		/*IF searchDto.contentEmail != null && searchDto.contentEmail != ''*/
			OR UPPER(email.EMAIL_CONTENT) LIKE concat('%',  concat(UPPER(/*searchDto.contentEmail*/), '%'))
		/*END*/
		)
	/*END*/
	/*IF searchDto.sendStatus != null && searchDto.sendStatus != ''*/
		AND email.SEND_STATUS = /*searchDto.sendStatus*/
	/*END*/
	/*IF searchDto.fromDate != null */
		AND CAST(/*searchDto.fromDate*/ AS date) <= CAST(email.SEND_DATE AS date)
	/*END*/
	/*IF searchDto.toDate != null */
		AND CAST(/*searchDto.toDate*/ AS date) >= CAST(email.SEND_DATE AS date) 
	/*END*/
	
	/*IF orders != null*/
	ORDER BY /*$orders*/cal.ID
	-- ELSE ORDER BY email.SEND_DATE DESC
	/*END*/
	
	/*BEGIN*/
	  /*IF offset != null*/
			OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
	  /*END*/
	/*END*/