SELECT
	COUNT(*)
FROM
    jca_position position with (nolock)
WHERE
    position.DELETED_ID = 0 
   	/*IF searchDto.companyId != null && searchDto.companyId != 0*/
	AND position.COMPANY_ID = /*searchDto.companyId*/
	/*END*/
	/*IF searchDto.companyId == null*/
	AND position.COMPANY_ID IS NULL
	/*END*/
	/*IF searchDto.companyId == 0 && !searchDto.companyAdmin*/
	AND (position.COMPANY_ID  IN /*searchDto.companyIdList*/()
	OR position.COMPANY_ID IS NULL)
	/*END*/
	/*BEGIN*/
	AND (
	/*IF searchDto.code != null && searchDto.code != ''*/
    OR UPPER(position.code) LIKE concat(concat('%',  UPPER(/*searchDto.code*/)), '%')
    /*END*/
	/*IF searchDto.name != null && searchDto.name != ''*/
	OR UPPER(position.name) LIKE concat(concat('%',  UPPER(/*searchDto.name*/)), '%')
	/*END*/
	/*IF searchDto.nameAbv != null && searchDto.nameAbv != ''*/
	OR UPPER(position.name_abv) LIKE concat(concat('%',  UPPER(/*searchDto.nameAbv*/)), '%')
	/*END*/
	/*IF searchDto.description != null && searchDto.description != ''*/
	OR UPPER(position.description) LIKE concat(concat('%',  UPPER(/*searchDto.description*/)), '%')
	/*END*/
	)
	/*END*/