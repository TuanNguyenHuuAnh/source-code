--
-- JcaConstantRepository_countJcaConstantByConditionSearchLike.sql

SELECT COUNT(*)
FROM JCA_CONSTANT con
WHERE 
    con.ACTIVED = 1
	/*IF jcaConstantSearchDto.groupCode != null && jcaConstantSearchDto.groupCode != ''*/
	AND con.GROUP_CODE LIKE CONCAT('%', /*jcaConstantSearchDto.groupCode*/'', '%' )
	/*END*/
	/*IF jcaConstantSearchDto.kind != null && jcaConstantSearchDto.kind != ''*/
	AND con.KIND LIKE CONCAT('%', /*jcaConstantSearchDto.kind*/'', '%' )
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