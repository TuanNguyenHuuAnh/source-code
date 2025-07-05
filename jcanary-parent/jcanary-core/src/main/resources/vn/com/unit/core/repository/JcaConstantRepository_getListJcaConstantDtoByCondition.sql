-- JcaConstantRepository_getListJcaConstantDtoByCondition.sql
SELECT	
		con.GROUP_ID					AS GROUP_ID
		,con.GROUP_CODE					AS GROUP_CODE
		,con.KIND						AS KIND
		,con.CODE                       AS CODE
		,con.GROUP_CODE                 AS GROUP_CODE
		,con.DISPLAY_ORDER              AS DISPLAY_ORDER
		,con.CAN_DELETE					AS CAN_DELETE
		, MAX(CASE WHEN LANG_ID = 1 THEN LANG_ID ELSE NULL END)  AS LANG_ID_EN
		, MAX(CASE WHEN LANG_ID = 2 THEN LANG_ID ELSE NULL END)  AS LANG_ID_VI
		, MAX(CASE WHEN LANG_ID = 1 THEN LANG_CODE ELSE NULL END)  AS LANG_ID_EN
		, MAX(CASE WHEN LANG_ID = 2 THEN LANG_CODE ELSE NULL END)  AS LANG_ID_VI
		, MAX(CASE WHEN LANG_ID = 1 THEN NAME ELSE NULL END)  AS NAME_EN
		, MAX(CASE WHEN LANG_ID = 2 THEN NAME ELSE NULL END)  AS NAME_VI
FROM JCA_CONSTANT con
WHERE 
	con.ACTIVED = 1
	/*IF jcaConstantSearchDto.groupCode != null && jcaConstantSearchDto.groupCode != ''*/
	AND con.GROUP_CODE LIKE CONCAT('%', /*jcaConstantSearchDto.groupCode*/'', '%')
	/*END*/
	/*IF jcaConstantSearchDto.kind != null && jcaConstantSearchDto.kind != ''*/
	AND con.KIND LIKE CONCAT('%', /*jcaConstantSearchDto.kind*/'', '%')
	/*END*/
	/*IF jcaConstantSearchDto.langCode != null && jcaConstantSearchDto.langCode != ''*/
	AND UPPER(con.LANG_CODE) LIKE CONCAT('%', UPPER(/*jcaConstantSearchDto.langCode*/''), '%' )
	/*END*/
	/*BEGIN*/
	AND
	(
		/*IF jcaConstantSearchDto.code != null && jcaConstantSearchDto.code != ''*/
	    OR UPPER(con.CODE) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaConstantSearchDto.code*/''), '%' ))
	   /*END*/
	   	/*IF jcaConstantSearchDto.name != null && jcaConstantSearchDto.name != ''*/
	    OR UPPER(con.NAME) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaConstantSearchDto.name*/''), '%' ))
	   /*END*/

    )
    /*END*/
GROUP BY GROUP_ID,GROUP_CODE, CODE,KIND, DISPLAY_ORDER, ACTIVED, CAN_DELETE, CONVERT(DATE, CREATED_DATE), CREATED_ID, CONVERT(DATE, UPDATED_DATE), UPDATED_ID
/*IF orders != null*/
ORDER BY /*$orders*/con.CODE
-- ELSE ORDER BY con.GROUP_ID DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/0 ROWS FETCH NEXT  /*size*/10 ROWS ONLY
  /*END*/
/*END*/