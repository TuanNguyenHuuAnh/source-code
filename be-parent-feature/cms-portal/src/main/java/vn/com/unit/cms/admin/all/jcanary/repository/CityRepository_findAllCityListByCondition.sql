--
-- CityRepository_findAllCityListByCondition.sql

SELECT
	city.id AS city_id,
	city.code AS city_code,
	cil.name AS city_name,
	city.m_country_id ,
	col.country_name,
	city.m_region_id,
	cl.name AS region_name,
	city.latitude,
	city.longtitude,
	city.note,
	city.description,
	CONCAT(city.m_country_id,'@',city.m_region_id) AS region_country,
	city.create_date
FROM
	jca_m_city as city
	LEFT JOIN JCA_LANGUAGE lang ON lang.code = /*languageCode*/
	LEFT JOIN jca_m_city_language cil ON city.id = cil.m_city_id AND cil.m_language_code = lang.code
	LEFT JOIN jca_m_region_language cl ON city.m_region_id = cl.m_region_id AND cl.m_language_code = lang.code
	LEFT JOIN jca_m_country_language col ON city.m_country_id = col.m_country_id AND lang.code = col.m_language_code
WHERE 
	city.delete_by IS NULL		
	/*IF cityDto.mRegionId != null*/
	AND city.m_region_id = /*cityDto.mRegionId*/
	/*END*/
	/*IF cityDto.mCountryId != null*/
	AND city.m_country_id = /*cityDto.mCountryId*/
	/*END*/
	/*IF cityDto.cityCode != null && cityDto.cityCode != ''*/
	AND city.code = /*cityDto.cityCode*/
	/*END*/
ORDER BY city.create_date DESC