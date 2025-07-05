--
-- CountryRepository_findAllCountryListByCondition.sql

SELECT
	cou.id as country_id,    
    cou.code as country_code,
    cl.country_name,
    cl.local_name,
    cou.latitude,
    cou.longtitude,
    cou.web_code,
    cou.phone_code,
    cou.note,
    cou.description,
    cou.create_date
FROM
	jca_m_country cou	
	/*IF languageCode != null && languageCode != ''*/
	LEFT JOIN JCA_LANGUAGE lang ON UPPER(lang.code) = UPPER(/*languageCode*/)
	/*END*/
	LEFT JOIN jca_m_country_language cl ON cou.id = cl.m_country_id AND cl.m_language_code = lang.code
WHERE
	cou.delete_by IS NULL	
	AND cl.country_name IS NOT NULL 
	AND cou.code IS NOT NULL
	/*IF countryDto.countryId != null*/
	AND cou.id =/*countryDto.countryId*/
	/*END*/
	/*IF countryDto.countryCode != null && countryDto.countryCode != ''*/
	AND cou.code =/*countryDto.countryCode*/
	/*END*/
	/*IF countryDto.webCode != null && countryDto.webCode != ''*/
	AND cou.web_code =/*countryDto.webCode*/
	/*END*/
	ORDER BY cou.create_date DESC
	

	
	
