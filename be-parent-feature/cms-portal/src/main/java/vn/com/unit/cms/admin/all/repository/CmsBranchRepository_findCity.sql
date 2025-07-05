select 
	 ta.code as ID
	,tb.name  as NAME
	,CONCAT(ta.code, ' - ', tb.name)   as TEXT
from JCA_M_CITY ta
	left join JCA_M_CITY_LANGUAGE tb 
on ( tb.M_CITY_ID = ta.id)
where tb.M_LANGUAGE_CODE =  UPPER(/*lang*/)