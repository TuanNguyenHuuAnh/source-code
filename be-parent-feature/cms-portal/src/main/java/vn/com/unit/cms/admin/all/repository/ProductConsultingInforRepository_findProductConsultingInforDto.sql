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
		AND UPPER(cityLang.m_language_code) = UPPER(/*languageCode*/'vi')
),
districtTable as(
		SELECT 
		    district.id as id,
		    lang.name AS name
		FROM jca_m_district district
			left join jca_m_district_language lang ON lang.m_district_id = district.id and lang.delete_date is null
		WHERE 
			district.delete_date IS NULL
			AND UPPER(lang.m_language_code) = UPPER(/*languageCode*/'')
)
SELECT
	  consulting.id as id
    , consulting.fullname 			AS  fullname
	, consulting.gender				AS	gender
	, case when   district.name is null and  city.name is null then ''
           when  district.name is not null and city.name is null then TO_CHAR(district.name)
           when  district.name is null and city.name is not null then TO_CHAR(city.name)
      else TO_CHAR(CONCAT(CONCAT(district.name, ', '),city.name)) end AS  address
    , consulting.phone_number		AS	phone_number
  	, prdLanguage.title				AS  product_name
  	, consulting.busines_name		AS	busines_name
	, consulting.m_customer_id		AS	customer_id
	, city.name           			AS  province
	, district.name                 AS  district
	, consulting.PROCESSING_STATUS as processing_status
	, city.id  as province_id
	, district.id  as district_id
FROM m_product_consulting_infor consulting
JOIN m_product_language prdLanguage ON (prdLanguage.m_product_id = consulting.product_id AND prdLanguage.delete_date IS NULL)
LEFT JOIN cityTable city on to_char(city.id) = TRIM(consulting.PROVINCE)
LEFT JOIN districtTable district on to_char(district.id) = TRIM(consulting.district)
WHERE
	consulting.delete_date IS NULL
	AND consulting.id = /*id*/
	AND prdLanguage.m_language_code = UPPER(/*languageCode*/'vi')
	
