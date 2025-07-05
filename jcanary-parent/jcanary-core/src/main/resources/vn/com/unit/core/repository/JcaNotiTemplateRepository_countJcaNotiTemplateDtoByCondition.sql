SELECT
	COUNT(noti.ID)
FROM 
	JCA_NOTI_TEMPLATE noti
WHERE 
	noti.DELETED_ID = 0
	/*IF searchDto.companyId != null*/
		AND noti.COMPANY_ID = /*searchDto.companyId*/
	/*END*/
	/*BEGIN*/
		AND (
		/*IF searchDto.name != null && searchDto.name != ''*/
			OR UPPER(noti.NAME) LIKE concat('%',  concat(UPPER(/*searchDto.name*/), '%'))
		/*END*/
		/*IF searchDto.code != null && searchDto.code != ''*/
			OR UPPER(noti.CODE) LIKE concat('%',  concat(UPPER(/*searchDto.code*/), '%'))
		/*END*/
		)
	/*END*/