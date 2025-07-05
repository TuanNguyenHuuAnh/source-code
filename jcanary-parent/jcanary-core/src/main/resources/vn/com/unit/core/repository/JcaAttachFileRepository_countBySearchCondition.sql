SELECT COUNT(attach.ID)
FROM
	JCA_ATTACH_FILE attach
WHERE 
	attach.DELETED_ID = 0
	/*IF searchDto.attachType != null && searchDto.attachType != ''*/
	AND attach.ATTACH_TYPE = /*searchDto.attachType*/
	/*END*/
	/*IF searchDto.companyId != null*/
	AND attach.COMPANY_ID = /*searchDto.companyId*/
	/*END*/
	/*IF searchDto.referenceId != null*/
	AND attach.REFERENCE_ID = /*searchDto.referenceId*/
	/*END*/
	/*IF searchDto.referenceKey != null && searchDto.referenceKey != ''*/
	AND attach.REFERENCE_KEY = /*searchDto.referenceKey*/
	/*END*/
	/*IF searchDto.fileName != null && searchDto.fileName != ''*/
	AND UPPER(replace(attach.FILE_NAME,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.fileName*/), '%' ))
	/*END*/