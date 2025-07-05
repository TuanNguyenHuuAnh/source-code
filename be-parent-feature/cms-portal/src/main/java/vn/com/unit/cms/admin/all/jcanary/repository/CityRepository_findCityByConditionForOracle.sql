SELECT
	  cityLang.name 	AS name
	, city.id           AS id
    , cityLang.name     AS text
FROM
	jca_m_city city
	JOIN jca_m_city_language cityLang ON (cityLang.m_city_id = city.id AND cityLang.delete_date IS NULL)
    JOIN jca_m_country country ON (country.id = city.m_country_id)
WHERE 
	city.delete_date IS NULL
	AND UPPER(cityLang.m_language_code) = UPPER(/*language*/'EN')
    AND country.code = /*countryCode*/
ORDER BY cityLang.name, city.create_date DESC