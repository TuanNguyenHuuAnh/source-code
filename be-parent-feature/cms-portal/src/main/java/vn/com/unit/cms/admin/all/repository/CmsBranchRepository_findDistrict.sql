SELECT
	 dis.ID		as id
	,dislang.name  as name
FROM 	JCA_M_CITY ta 
LEFT JOIN JCA_M_CITY_LANGUAGE tb  
	  on ( tb.M_CITY_ID = ta.id) 
	  AND tb.delete_date is null
	
LEFT JOIN JCA_M_DISTRICT dis
	  on dis.M_CITY_ID = ta.id
	
LEFT JOIN JCA_M_DISTRICT_LANGUAGE dislang
	  on dis.id = dislang.M_DISTRICT_ID
WHERE 	ta.delete_date is null
	AND ta.ACTIVE_FLAG = 1
	AND ta.code = /*city*/'TBRVT'
	AND UPPER(tb.M_LANGUAGE_CODE) = UPPER(/*lang*/)
    AND UPPER(dislang.M_LANGUAGE_CODE) = UPPER(/*lang*/);
