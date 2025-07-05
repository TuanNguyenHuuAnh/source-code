SELECT 
	COUNT(email.ID)
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
	
	
	