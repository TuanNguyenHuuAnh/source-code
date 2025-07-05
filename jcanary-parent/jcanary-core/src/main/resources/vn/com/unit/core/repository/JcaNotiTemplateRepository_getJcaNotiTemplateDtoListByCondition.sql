SELECT
	noti.ID					AS ID
	, noti.CODE				AS CODE
	, noti.NAME				AS NAME
	, noti.DESCRIPTION		AS DESCRIPTION
	, noti.COMPANY_ID		AS COMPANY_ID
	, com.NAME				AS COMPANY_NAME
FROM 
	JCA_NOTI_TEMPLATE noti
LEFT JOIN 
	JCA_COMPANY com 
	ON noti.COMPANY_ID = com.ID AND com.DELETED_ID = 0
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
	/*IF orders != null*/
	ORDER BY /*$orders*/noti.ID
	-- ELSE ORDER BY noti.UPDATED_DATE DESC
	/*END*/
	
	/*BEGIN*/
	  /*IF offset != null*/
			OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
	  /*END*/
	/*END*/