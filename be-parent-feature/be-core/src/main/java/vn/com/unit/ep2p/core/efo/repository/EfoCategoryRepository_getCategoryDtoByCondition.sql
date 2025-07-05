SELECT 
	cat.ID 				as CATEGORY_ID
	,cat.COMPANY_ID 	as COMPANY_ID
	,lang.NAME 			as CATEGORY_NAME
	,cat.DESCRIPTION 	as DESCRIPTION
	,cat.DISPLAY_ORDER 	as DISPLAY_ORDER
	,cat.ACTIVED 	as ACTIVED
FROM 
	EFO_CATEGORY cat
LEFT JOIN 
    EFO_CATEGORY_LANG lang
ON
    cat.ID = lang.category_id
    AND Upper(lang.lang_code) = Upper('en')
WHERE
	cat.DELETED_ID = 0
	/*IF efoCategorySearchDto.name != null && efoCategorySearchDto.name != ''*/
	OR UPPER(replace(cat.NAME,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*efoCategorySearchDto.name*/), '%' ))
	/*END*/
	
	/*IF efoCategorySearchDto.description != null && efoCategorySearchDto.description != ''*/
	OR UPPER(replace(cat.DESCRIPTION,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*efoCategorySearchDto.description*/), '%' ))
	/*END*/
		
	/*IF efoCategorySearchDto.actived != null*/
	AND cat.ACTIVED = /*efoCategorySearchDto.actived*/
	/*END*/	
		    
	/*IF efoCategorySearchDto.companyId != null*/
	AND cat.COMPANY_ID = /*efoCategorySearchDto.companyId*/
	/*END*/
/*IF orders != null*/
ORDER BY /*$orders*/name
-- ELSE ORDER BY cat.UPDATED_DATE DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/