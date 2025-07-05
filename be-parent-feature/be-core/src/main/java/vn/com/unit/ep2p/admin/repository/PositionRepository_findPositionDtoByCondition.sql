SELECT
	position.id 	       	AS position_id
	, position.code         AS code
    , position.name   	 	AS name
    , position.name_abv 	AS name_abv
    , position.description 	AS description
    , position.CREATED_DATE 	AS CREATED_DATE
    , status_code.code      AS status_code
    , co.NAME           AS company_name
	, case when co.name is null then N'ZZZ' else co.name end AS order_name
FROM
	jca_position position
LEFT JOIN
	jca_company co ON position.company_id = co.id
	AND co.DELETED_ID = 0	
LEFT JOIN
    JCA_CONSTANT status_code
ON
    status_code.GROUP_CODE = 'JCA_ADMIN_CHECKBOX'
    AND status_code.KIND = 'CHECKBOX_ACTIVED'
   AND status_code.code= position.actived
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
	
	GROUP BY position.id,position.code,position.name,position.name_abv,position.description,position.CREATED_DATE,status_code.code, co.NAME 
	
	ORDER BY 
		order_name,
		position.CREATED_DATE DESC
	OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY