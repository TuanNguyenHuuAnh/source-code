WITH countJcaConstant AS(
        SELECT
            count(*)            AS  countJcaConstant
        FROM
            JCA_CONSTANT con
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
			GROUP BY GROUP_ID,GROUP_CODE, CODE,KIND, DISPLAY_ORDER, ACTIVED, CONVERT(DATE, CREATED_DATE), CREATED_ID, CONVERT(DATE, UPDATED_DATE), UPDATED_ID)
		,countAll AS(
        SELECT countJcaConstant FROM countJcaConstant
		)
SELECT count(countJcaConstant) FROM countAll;