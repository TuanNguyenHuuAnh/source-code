SELECT
	  position.ID 	       									 AS POSITION_ID
	, position.CODE         								 AS CODE
    , position.NAME   	 									 AS NAME
    , position.NAME_ABV 									 AS NAME_ABV
    , position.DESCRIPTION 									 AS DESCRIPTION
    , position.COMPANY_ID									 AS COMPANY_ID
    , position.ACTIVED									 	 AS ACTIVED
    , position.CREATED_DATE 								 AS CREATED_DATE
--    , status_code.CODE      								 AS STATUS_CODE
--   , co.NAME           									 AS COMPANY_NAME
--	, case when co.name is null then N'ZZZ' else co.name end AS ORDER_NAME
FROM
	JCA_POSITION position
--LEFT JOIN
--	jca_company co 
--	ON position.COMPANY_ID = co.ID
--	AND co.DELETED_ID = 0
--LEFT JOIN
--    JCA_CONSTANT_DISPLAY status_code
--ON
--    status_code.TYPE = 'M05'
--    AND status_code.CAT = position.ACTIVED
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


/*IF orders != null*/
ORDER BY /*$orders*/CREATED_DATE
-- ELSE ORDER BY position.CREATED_DATE DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/