SELECT
	COUNT(1)
FROM
    JCA_POSITION position   
WHERE
    position.DELETED_ID = 0 
   	/*IF jcaPositionSearchDto.companyId != null && jcaPositionSearchDto.companyId != 0*/
	AND position.COMPANY_ID = /*jcaPositionSearchDto.companyId*/
	/*END*/
	/*IF jcaPositionSearchDto.companyId == null*/
	AND position.COMPANY_ID IS NULL
	/*END*/
	/*IF jcaPositionSearchDto.companyId == 0 && !jcaPositionSearchDto.companyAdmin*/
	AND (position.COMPANY_ID  IN /*jcaPositionSearchDto.companyIdList*/()
	OR position.COMPANY_ID IS NULL)
	/*END*/
	
	/*IF jcaPositionSearchDto.actived != null*/
	AND position.ACTIVED = /*jcaPositionSearchDto.actived*/
	/*END*/
	
	/*BEGIN*/
	AND (
	/*IF jcaPositionSearchDto.code != null && jcaPositionSearchDto.code != ''*/
    OR UPPER(position.CODE) LIKE concat(concat('%',  UPPER(/*jcaPositionSearchDto.code*/)), '%')
    /*END*/
	/*IF jcaPositionSearchDto.name != null && jcaPositionSearchDto.name != ''*/
	OR UPPER(position.NAME) LIKE concat(concat('%',  UPPER(/*jcaPositionSearchDto.name*/)), '%')
	/*END*/
	/*IF jcaPositionSearchDto.nameAbv != null && jcaPositionSearchDto.nameAbv != ''*/
	OR UPPER(position.NAME_ABV) LIKE concat(concat('%',  UPPER(/*jcaPositionSearchDto.nameAbv*/)), '%')
	/*END*/
	/*IF jcaPositionSearchDto.description != null && jcaPositionSearchDto.description != ''*/
	OR UPPER(position.DESCRIPTION) LIKE concat(concat('%',  UPPER(/*jcaPositionSearchDto.description*/)), '%')
	/*END*/
	)
	/*END*/