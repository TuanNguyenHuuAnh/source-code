select email.id as email_id
	, email.SEND_STATUS AS STATUS_SEND_MAIL
	, email.*
from JCA_EMAIL email
WHERE DELETED_ID = 0
	/*IF searchDto.companyId != null && searchDto.companyId != 0*/
	AND email.COMPANY_ID = /*searchDto.companyId*/
	/*END*/
	/*IF searchDto.companyId == null*/
	AND email.COMPANY_ID IS NULL
	/*END*/
	/*IF searchDto.companyId == 0 && !searchDto.companyAdmin*/
	AND (email.COMPANY_ID  IN /*searchDto.companyIdList*/()
	OR email.COMPANY_ID IS NULL)
	/*END*/
	/*BEGIN*/
	AND (
	/*IF searchDto.receiveAddress != null && searchDto.receiveAddress != ''*/
	OR UPPER(email.receive_address) LIKE concat('%',  concat(UPPER(/*searchDto.receiveAddress*/), '%'))
	/*END*/
	/*IF searchDto.senderAddress != null && searchDto.senderAddress != ''*/
	OR UPPER(email.sender_address) LIKE concat('%',  concat(UPPER(/*searchDto.senderAddress*/), '%'))
	/*END*/
	/*IF searchDto.subject != null && searchDto.subject != ''*/
	OR UPPER(email.subject) LIKE concat('%',  concat(UPPER(/*searchDto.subject*/), '%'))
	/*END*/
	/*IF searchDto.content != null && searchDto.content != ''*/
	OR UPPER(email.email_content) LIKE concat('%',  concat(UPPER(/*searchDto.content*/), '%'))
	/*END*/
	)
	/*END*/
	/*IF searchDto.statusEmail != null  && searchDto.statusEmail != ''*/
	AND email.status_send_mail = /*searchDto.statusEmail*/
	/*END*/
	/*IF searchDto.fromDate != null */
	AND /*searchDto.fromDate*/ <= email.send_date
	/*END*/
	/*IF searchDto.toDate != null */
	AND /*searchDto.toDate*/ >= email.send_date
	/*END*/
	
	ORDER BY email.send_date DESC
	
	OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY