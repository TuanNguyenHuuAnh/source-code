SELECT
	 ta.code as ID
	,tb.name  as NAME
	,CONCAT(ta.code, ' - ', tb.name)   as TEXT
FROM 
	JCA_M_CITY ta 
LEFT JOIN JCA_M_CITY_LANGUAGE tb  
	on ( tb.M_CITY_ID = ta.id) 
	AND tb.delete_date is null
WHERE 
	ta.delete_date is null
	AND ta.ACTIVE_FLAG = 1
	AND UPPER(tb.M_LANGUAGE_CODE) = UPPER(/*lang*/);