select 
	 ta.id as ID
	,tb.name  as NAME
	,CONCAT(ta.code, ' - ', tb.name)   as TEXT
from JCA_M_REGION ta
	left join JCA_M_REGION_LANGUAGE tb 
on ( tb.M_REGION_ID = ta.id)
where tb.M_LANGUAGE_CODE =  UPPER(/*lang*/)