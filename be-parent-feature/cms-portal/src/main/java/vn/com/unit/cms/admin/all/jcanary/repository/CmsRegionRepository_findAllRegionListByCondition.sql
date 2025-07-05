--
-- CmsRegionRepository_findAllRegionListByCondition.sql

SELECT
	region.id as region_id,    
    region.code as region_code,
    region.m_country_id,
    cl.name AS region_name, 
    col.country_name,
    region.note,
    region.description,
    region.create_date
FROM
	jca_m_region region	
	LEFT JOIN JCA_LANGUAGE lang ON UPPER(lang.code) = UPPER(/*languageCode*/)
	LEFT JOIN jca_m_region_language cl ON region.id = cl.m_region_id AND cl.m_language_code = lang.code
	LEFT JOIN jca_m_country_language col ON region.m_country_id = col.m_country_id AND lang.code = col.m_language_code
WHERE
	region.delete_by IS NULL	
	/*IF countryId != null*/
	AND region.m_country_id = /*countryId*/
	/*END*/
	ORDER BY region.create_date DESC
	

	
	
	