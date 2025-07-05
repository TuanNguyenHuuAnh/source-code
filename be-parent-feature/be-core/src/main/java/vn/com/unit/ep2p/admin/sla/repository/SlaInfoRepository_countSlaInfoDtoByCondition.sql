SELECT COUNT(*) FROM APP_SLA_INFO
WHERE
	DELETED_ID IS NULL
	/*IF searchDto.businessCode != null && searchDto.businessCode != ''*/
	AND BUSINESS_ID = /*searchDto.businessCode*/''
	/*END*/
	/*IF searchDto.processCode != null && searchDto.processCode != ''*/
	AND PROCESS_ID = /*searchDto.processCode*/''
	/*END*/
	/*IF searchDto.name != null && searchDto.name != ''*/
	AND SLA_NAME LIKE CONCAT(CONCAT('%',/*searchDto.name*/''),'%')
	/*END*/
	/*IF searchDto.companyIdList != null*/
	AND COMPANY_ID IN /*searchDto.companyIdList*/()
	/*END*/
