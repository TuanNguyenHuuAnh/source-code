WITH cityTable as (
		SELECT
		   cityLang.name 	AS name
		 , city.id           AS id
	FROM
		jca_m_city city
		JOIN jca_m_city_language cityLang ON (cityLang.m_city_id = city.id AND cityLang.delete_date IS NULL)
	    JOIN jca_m_country country ON (country.id = city.m_country_id)
	WHERE 
		city.delete_date IS NULL
		AND UPPER(cityLang.m_language_code) = UPPER(/*searchCond.languageCode*/'')
),
districtTable as(
		SELECT 
		    district.id as id,
		    lang.name AS name
		FROM jca_m_district district
			left join jca_m_district_language lang ON lang.m_district_id = district.id and lang.delete_date is null
		WHERE 
			district.delete_date IS NULL
			AND UPPER(lang.m_language_code) = UPPER(/*searchCond.languageCode*/'')
)
SELECT
    ROW_NUMBER() OVER (ORDER BY consulting.create_date DESC)	AS  stt
	, consulting.fullname 										AS  fullname
	, case when   district.name is null and  city.name is null then ''
           when  district.name is not null and city.name is null then TO_CHAR(district.name)
           when  district.name is null and city.name is not null then TO_CHAR(city.name)
      else TO_CHAR(CONCAT(CONCAT(district.name, ', '),city.name)) end  AS  address
    , consulting.phone_number									AS	phone_number
  	, prdLanguage.title											AS  product_name
    , city.name													AS  province
	, district.name												AS  district
  	, consulting.create_date									AS	create_date
  	, to_char(consulting.create_date, 'HH24:MI')				AS	create_time
  	, consulting.comment_name as comment_name
     , case when UPPER(TRIM(consulting.PROCESSING_STATUS)) = UPPER(TRIM('WAITING')) then 'Đang chờ' 
           when UPPER(TRIM(consulting.PROCESSING_STATUS)) = UPPER(TRIM('PROCESSING')) then 'Đã tiếp nhận và đang chờ xử lý' 
           when UPPER(TRIM(consulting.PROCESSING_STATUS)) = UPPER(TRIM('DONE')) then 'Đã hoàn thành' 
           when UPPER(TRIM(consulting.PROCESSING_STATUS)) = UPPER(TRIM('REJECTED')) then 'Đã từ chối'
           else '' end   as processing_status
    , consulting.processed_user as process_user
    , to_char(consulting.update_date, 'dd/MM/yyyy HH24:MI:SS') as update_date
FROM m_product_consulting_infor consulting
JOIN m_product_language prdLanguage ON (prdLanguage.m_product_id = consulting.product_id AND prdLanguage.delete_date IS NULL)
LEFT JOIN cityTable city on to_char(city.id) = TRIM(consulting.PROVINCE)
LEFT JOIN districtTable district on to_char(district.id) = TRIM(consulting.district)
WHERE
	consulting.delete_date IS NULL
	AND prdLanguage.m_language_code = UPPER(/*searchCond.languageCode*/'')
	
	/*IF searchCond.businesName != null && searchCond.businesName != ''*/
	AND UPPER(consulting.busines_name) LIKE ('%'||UPPER(TRIM(/*searchCond.businesName*/))||'%')
	/*END*/
	
	/*IF searchCond.fullname != null && searchCond.fullname != ''*/
	AND UPPER(consulting.fullname) LIKE ('%'||UPPER(TRIM(/*searchCond.fullname*/))||'%')
	/*END*/
	
	/*IF searchCond.address != null && searchCond.address != ''*/
	AND UPPER(concat(district.name,city.name)) LIKE ('%'||UPPER(TRIM(/*searchCond.address*/''))||'%')
	/*END*/
	
	/*IF searchCond.phoneNumber != null && searchCond.phoneNumber != ''*/
	AND UPPER(consulting.phone_number) LIKE ('%'||UPPER(TRIM(/*searchCond.phoneNumber*/))||'%')
	/*END*/
	
	/*IF searchCond.describeRequest != null && searchCond.describeRequest != ''*/
	AND UPPER(consulting.describe_request) LIKE ('%'||UPPER(TRIM(/*searchCond.describeRequest*/))||'%')
	/*END*/
	
	/*IF searchCond.customerId != null*/
	AND UPPER(consulting.m_customer_id) LIKE ('%'||UPPER(TRIM(/*searchCond.customerId*/))||'%')
	/*END*/
	/*IF searchCond.processingStatus != null && searchCond.processingStatus != ''*/
	AND UPPER(consulting.processing_status) LIKE ('%'||  UPPER(/*searchCond.processingStatus*/) || '%')
	/*END*/
	
    /*IF searchCond.fromDate != null*/
    AND TRUNC(consulting.create_date)  >= TRUNC(/*searchCond.fromDate*/)
    /*END*/
    
    /*IF searchCond.toDate != null*/
    AND TRUNC(consulting.create_date) <= TRUNC(/*searchCond.toDate*/)
    /*END*/
ORDER BY consulting.create_date DESC
	