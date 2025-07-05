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
    clanguage.title as name,
    clanguage.title as title,
    clanguage.title as parent_name,
    categ.id as reference_id,
    categ.INVESTOR_CATEGORY_PARENT_ID as parent_id,
    categ.INVESTOR_CATEGORY_TYPE as category_type,
    case when categ.INVESTOR_CATEGORY_TYPE = 0 then '0. None'
         when categ.INVESTOR_CATEGORY_TYPE = 1 then '1. News'
         when categ.INVESTOR_CATEGORY_TYPE = 2 then '2. Template List Link'
         when categ.INVESTOR_CATEGORY_TYPE = 3 then '3. Template Picture and List Link'
         when categ.INVESTOR_CATEGORY_TYPE = 4 then '4. Template Picture and Table'
         when categ.INVESTOR_CATEGORY_TYPE = 5 then '5. Template Split Line Link'
         when categ.INVESTOR_CATEGORY_TYPE = 6 then '6. Template Table'
         when categ.INVESTOR_CATEGORY_TYPE = 7 then '7. Template Table and List Link'
          else '' end as category_type_name,
   	categ.investor_category_location_left   as category_location_left,
    categ.investor_category_location_right_top	as category_location_right_top,
    categ.investor_category_location_right_bottom   as category_location_right_bottom,
    CASE WHEN categ.investor_category_location_left = 1 THEN 'x'
			ELSE  ''
			END							AS location_left,
    CASE WHEN categ.investor_category_location_right_top = 1 THEN 'x'
			ELSE  ''
			END							AS location_right_top,
    CASE WHEN categ.investor_category_location_right_bottom = 1 THEN 'x'
			ELSE  ''
			END							AS location_right_bottom,
    CASE WHEN categ.status = 1 THEN /*searchDto.statusName*/'Lưu nháp'
			ELSE  stepStatus.STATUS_NAME
		END  									AS STATUS_NAME
    , categ.create_date as create_date
    , categ.CREATE_BY as create_by
    , categ.approve_by				AS 	approve_by
  	, categ.approve_date			AS 	approve_date
  	, categ.publish_by				AS 	publish_by
  	, categ.publish_date			AS 	publish_date
    , categ.sort 					AS	sort
    , categ.enable					AS	enabled
     , (SELECT count(1) FROM M_INVESTOR investor 
	  	WHERE    investor.delete_date IS NULL 
	  		 AND investor.delete_by IS NULL
	  		 AND investor.CUSTOMER_TYPE_ID = categ.CUSTOMER_TYPE_ID
	    	 AND investor.M_INVESTOR_CATEGORY_ID = categ.id)  as number_investor
	 , categ.status as status
FROM m_investor_category categ
LEFT JOIN m_investor_category_language clanguage ON (categ.id = clanguage.m_investor_category_id AND clanguage.delete_date is null)
LEFT JOIN stepStatus	ON stepStatus.process_id = categ.process_id
                      and stepStatus.step_no = categ.status
WHERE
	categ.delete_date is null
	
	AND UPPER(clanguage.m_language_code) = UPPER(/*searchDto.languageCode*/'')

	AND categ.customer_type_id = /*searchDto.customerId*/
  
 	 /*IF searchDto.categoryType != null*/
      AND UPPER(categ.investor_category_type)  = UPPER(/*searchDto.categoryType*/)
    /*END*/
	
	/*IF searchDto.status != null*/
      AND UPPER(categ.status)  = UPPER(/*searchDto.status*/)
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
		
	/*IF searchDto.parentId != null */	
		OR categ.INVESTOR_CATEGORY_PARENT_ID = /*searchDto.parentId*/
	/*END*/
ORDER BY categ.sort ASC, categ.create_date DESC
