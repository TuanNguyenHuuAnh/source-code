SELECT 	TBL_MAIN.ID,
		TBL_MAIN.CODE,
       	TBL_LANG.TITLE AS name,
       	TBL_MAIN.SORT
       
FROM    M_FAQS TBL_MAIN

JOIN    M_FAQS_LANGUAGE TBL_LANG

ON      TBL_MAIN.ID = TBL_LANG.M_FAQS_ID

WHERE   TBL_MAIN.DELETE_DATE IS NULL

		AND TBL_MAIN.ENABLED = 1

        AND TBL_LANG.M_LANGUAGE_CODE = UPPER(/*lang*/)
        
        AND TBL_MAIN.M_CUSTOMER_TYPE_ID = /*customerId*/
        
        /*IF typeId != null*/
        AND TBL_MAIN.M_FAQS_TYPE_ID = /*typeId*/
        /*END*/
        
        /*IF categoryId != null*/
        AND TBL_MAIN.M_FAQS_CATEGORY_ID = /*categoryId*/
        /*END*/
        
        /*IF id != null*/
        AND TBL_MAIN.ID != /*id*/
        /*END*/
ORDER BY TBL_LANG.TITLE ASC, TBL_MAIN.SORT ASC