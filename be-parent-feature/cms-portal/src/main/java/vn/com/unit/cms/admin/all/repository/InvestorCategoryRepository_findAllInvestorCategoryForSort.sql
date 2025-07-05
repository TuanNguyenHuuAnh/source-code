SELECT 	
    TBL_LANG.title			AS	name
	, TBL_MAIN.*
       
FROM 	M_INVESTOR_CATEGORY TBL_MAIN

JOIN 	M_INVESTOR_CATEGORY_LANGUAGE TBL_LANG

ON 		TBL_MAIN.ID = TBL_LANG.M_INVESTOR_CATEGORY_ID

WHERE   TBL_MAIN.DELETE_DATE IS NULL

		AND TBL_MAIN.ENABLE = 1
	
    AND UPPER(TBL_LANG.M_LANGUAGE_CODE) = UPPER(/*language*/'')
    
      /*IF searchDto.id != null*/
      AND TBL_MAIN.ID != /*searchDto.id*/
      /*END*/
      
       /*IF searchDto.parentId != null*/
      AND TBL_MAIN.INVESTOR_CATEGORY_PARENT_ID = /*searchDto.parentId*/
      /*END*/
      
ORDER BY TBL_MAIN.SORT ASC,TBL_MAIN.CREATE_DATE DESC;