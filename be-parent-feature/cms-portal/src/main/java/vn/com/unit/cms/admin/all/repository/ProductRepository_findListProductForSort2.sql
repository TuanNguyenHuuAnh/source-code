SELECT 	TBL_MAIN.id as id,
        TBL_LANG.title as text,
        TBL_LANG.title as name       
FROM 	M_PRODUCT TBL_MAIN

JOIN 	M_PRODUCT_LANGUAGE TBL_LANG

ON 		TBL_MAIN.ID = TBL_LANG.M_PRODUCT_ID

WHERE         TBL_MAIN.DELETE_DATE IS NULL
	
	        AND TBL_MAIN.ENABLED = 1
	
	        AND UPPER(TBL_LANG.M_LANGUAGE_CODE) = UPPER(/*languageCode*/'')
	        
	        AND TBL_MAIN.M_CUSTOMER_TYPE_ID = /*customerId*/
        
		/*IF categoryId != null*/
	        AND TBL_MAIN.M_PRODUCT_CATEGORY_ID = /*categoryId*/
	    /*END*/
			
		/*IF categorySubId != null*/
			AND TBL_MAIN.M_PRODUCT_CATEGORY_SUB_ID = /*categorySubId*/
		/*END*/
	        
	    /*IF productId != null*/
	        AND TBL_MAIN.ID != /*productId*/
	    /*END*/
        
ORDER BY TBL_MAIN.SORT ASC,TBL_MAIN.CREATE_DATE DESC