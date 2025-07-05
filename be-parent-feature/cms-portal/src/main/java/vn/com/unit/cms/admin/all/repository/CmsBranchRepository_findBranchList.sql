
WITH TBL_DATA AS (

    SELECT
        br.id												AS	ID
         ,br.type											AS	TYPE
         ,br.name											AS	TITLE
         ,br.address											AS	ADDRESS
         ,br.CITY_NAME											AS	CITY
         ,br.DISTRICT_NAME												as DISTRICT
         ,br.active_flag										AS	ACTIVE_FLAG
         ,br.create_by										AS	create_by
         ,br.create_date										AS	create_date
         ,br.update_by										AS	update_by
         ,br.update_date										AS	update_date
         ,cb.name 											AS	TYPE_NAME
         ,br.active_flag										AS	ENABLED
         ,br.name											AS	NAME
    FROM
        jca_m_branch br
            LEFT JOIN JCA_CONSTANT cb
                      ON (cb.CODE = br.TYPE
                          AND cb.LANG_CODE = UPPER(/*searchDto.languageCode*/'vi')
                          AND cb.GROUP_CODE = 'BRANCH_TYPE')
            LEFT JOIN JCA_M_REGION reg
                      on (reg.ID = br.REGION and reg.DELETE_DATE is null)
            LEFT JOIN JCA_M_REGION_LANGUAGE regl
                      on ( regl.M_REGION_ID = reg.id
                          and regl.M_LANGUAGE_CODE =  UPPER(/*searchDto.languageCode*/'vi'))
    WHERE
        br.delete_by IS NULL

)
SELECT *
FROM TBL_DATA TBL
WHERE
	1 = 1
	
	/*IF searchDto.querySearch != NULL && searchDto.querySearch != ''*/
	AND
	(
		/*$searchDto.querySearch*/'bao'
	)
	/*END*/
/*IF orders != null*/
	ORDER BY /*$orders*/TBL.ID
-- ELSE ORDER BY ISNULL(TBL.update_date, TBL.create_date) DESC
/*END*/
/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/
