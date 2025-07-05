SELECT 	TBL_MAIN.*
       
FROM 	M_PRODUCT TBL_MAIN

JOIN 	M_PRODUCT_LANGUAGE TBL_LANG

ON 		TBL_MAIN.ID = TBL_LANG.M_PRODUCT_ID

WHERE   TBL_MAIN.DELETE_DATE IS NULL

		AND TBL_MAIN.ENABLED = 1

        AND TBL_LANG.M_LANGUAGE_CODE = UPPER(/*languageCode*/'')
        
        AND TBL_MAIN.M_CUSTOMER_TYPE_ID = /*categoryEdit.typeId*/
        
        /*IF categoryEdit.categoryId != null*/
		AND TBL_MAIN.M_PRODUCT_CATEGORY_ID = /*categoryEdit.categoryId*/
		/*END*/
		
		/*IF categoryEdit.categorySubId != null*/
		AND TBL_MAIN.M_PRODUCT_CATEGORY_SUB_ID = /*categoryEdit.categorySubId*/
		/*END*/
        
        /*IF categoryEdit.id != null*/
        AND TBL_MAIN.ID != /*categoryEdit.id*/
        /*END*/
ORDER BY TBL_MAIN.SORT ASC,TBL_MAIN.CREATE_DATE DESC