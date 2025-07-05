SELECT 	
	TBL_LANG.title			AS	title
	, TBL_MAIN.*
       
FROM 	M_INVESTOR TBL_MAIN

JOIN 	M_INVESTOR_LANGUAGE TBL_LANG

ON 		TBL_MAIN.ID = TBL_LANG.M_INVESTOR_ID

WHERE   TBL_MAIN.DELETE_DATE IS NULL

		AND TBL_MAIN.ENABLED = 1
		
		AND TBL_MAIN.STATUS != 100

        AND UPPER(TBL_LANG.M_LANGUAGE_CODE) = UPPER(/*language*/'')
       
        /*IF searchDto.categoryId != null*/
		AND TBL_MAIN.M_INVESTOR_CATEGORY_ID = /*searchDto.categoryId*/
		/*END*/
        
        /*IF searchDto.id != null*/
        AND TBL_MAIN.ID != /*searchDto.id*/
        /*END*/
ORDER BY TBL_MAIN.SORT ASC,TBL_MAIN.CREATE_DATE DESC