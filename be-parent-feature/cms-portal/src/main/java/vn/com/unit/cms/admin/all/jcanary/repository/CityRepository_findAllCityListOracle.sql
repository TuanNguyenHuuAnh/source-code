SELECT
	  cityLang.name 	AS	city_name
	, city.id 			AS	city_id
	, city.latitude		AS	latitude
	, city.longtitude	AS	longtitude
FROM
	jca_m_city city
	JOIN jca_m_city_language cityLang ON (cityLang.m_city_id = city.id AND cityLang.delete_date IS NULL)
WHERE 
	city.delete_date IS NULL
	AND UPPER(cityLang.m_language_code) = UPPER(/*languageCode*/'')
ORDER BY NLSSORT(cityLang.name, 'NLS_SORT=generic_m'), city.create_date DESC