SELECT
	attach.ID				AS ATTACH_FILE_ID
	, attach.FILE_NAME		AS FILE_NAME
	, attach.FILE_SIZE		AS FILE_SIZE
	, attach.FILE_TYPE		AS FILE_TYPE
	, attach.CONTENT_TYPE	AS CONTENT_TYPE
	, attach.COMPANY_ID		AS COMPANY_ID
	, attach.REPOSITORY_ID	AS REPOSITORY_ID
	, attach.FILE_PATH		AS FILE_PATH
	, attach.REFERENCE_ID	AS REFERENCE_ID
	, attach.REFERENCE_KEY	AS REFERENCE_KEY
	, attach.ATTACH_TYPE	AS ATTACH_TYPE
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
/*IF orders != null*/
ORDER BY /*$orders*/attach.ID
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/
	