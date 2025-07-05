WITH stepStatus AS (
	SELECT DISTINCT
		  process.ID				AS		process_id
		, step.STATUS				AS		status_code
		, CASE WHEN statusLang.STATUS_NAME IS NULL THEN processStatus.STATUS_NAME 
			ELSE  statusLang.STATUS_NAME END  AS STATUS_NAME
		, step.STEPNO				AS		step_no
	FROM
	    JPR_PROCESS process
	
	JOIN JPR_STEP step						-- Get step of process
		ON step.PROCESSID = process.id
		
	JOIN JPR_PROCESS_STATUS processStatus		-- Get all status
		ON processStatus.business_code = process.BUSINESSCODE
		AND processStatus.status_code = step.STATUS
		
	LEFT JOIN JPR_PROCESS_STATUS_LANG STATUSLANG
	        ON statusLang.MAIN_ID = processStatus.ID
	        AND UPPER(statusLang.LANG_CODE) = UPPER(/*searchDto.languageCode*/'')
)

SELECT
    categ.id as id,
    categ.code as code,
    clanguage.title as title,
    case when categ.INVESTOR_CATEGORY_TYPE = 0 then '0. None'
         when categ.INVESTOR_CATEGORY_TYPE = 1 then '1. News'
         when categ.INVESTOR_CATEGORY_TYPE = 2 then '2. Template List Link'
         when categ.INVESTOR_CATEGORY_TYPE = 3 then '3. Template Picture and List Link'
         when categ.INVESTOR_CATEGORY_TYPE = 4 then '4. Template Picture and Table'
         when categ.INVESTOR_CATEGORY_TYPE = 5 then '5. Template Split Line Link'
         when categ.INVESTOR_CATEGORY_TYPE = 6 then '6. Template Table'
         when categ.INVESTOR_CATEGORY_TYPE = 7 then '7. Template Table and List Link'
          else '' end as category_type_name,
    CASE WHEN categ.investor_category_location_left = 1 THEN 'x'
			ELSE  ''
			END							AS location_left,
    CASE WHEN categ.investor_category_location_right_top = 1 THEN 'x'
			ELSE  ''
			END							AS location_right_top,
    CASE WHEN categ.investor_category_location_right_bottom = 1 THEN 'x'
			ELSE  ''
			END							AS location_right_bottom,
    CASE WHEN categ.status = 1 THEN N'Lưu nháp'
			ELSE  stepStatus.STATUS_NAME
		END  									AS status_name,
    	categ.create_date as create_date,
    	categ.CREATE_BY as create_by,
    	categ.INVESTOR_CATEGORY_PARENT_ID as parent_id
FROM m_investor_category categ
LEFT JOIN m_investor_category_language clanguage ON (categ.id = clanguage.m_investor_category_id AND clanguage.delete_date is null)
LEFT JOIN stepStatus	ON stepStatus.process_id = categ.process_id
                      and stepStatus.step_no = categ.status
WHERE
	categ.delete_date is null
	
	AND UPPER(clanguage.m_language_code) = UPPER(/*searchDto.languageCode*/'')

	AND categ.customer_type_id = /*searchDto.customerId*/
  
 	 /*IF searchDto.categoryType != null*/
      AND categ.investor_category_type like concat('%',/*searchDto.categoryType*/,'%')
    /*END*/
	
	/*IF searchDto.status != null*/
      AND categ.status  like concat('%',/*searchDto.status*/,'%')
    /*END*/
      
    /*IF searchDto.code != null && searchDto.code != ''*/
		AND categ.code LIKE concat('%',/*searchDto.code*/,'%')
	/*END*/
		
	 /*IF searchDto.name != null && searchDto.name != ''*/
		AND clanguage.title LIKE concat('%',/*searchDto.name*/,'%')
	/*END*/	
	
	/*IF searchDto.id != null */	
		OR categ.id = /*searchDto.id*/
	/*END*/
ORDER BY categ.id ASC
