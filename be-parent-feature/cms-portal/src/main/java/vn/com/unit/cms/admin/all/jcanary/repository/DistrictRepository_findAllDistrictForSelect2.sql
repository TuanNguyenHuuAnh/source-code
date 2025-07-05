SELECT
	distinct(TRIM(dl.name)) as id,
    dl.name as text,
    dl.name as name
FROM
    jca_m_district district
    LEFT JOIN jca_m_district_language dl on district.id = dl.m_district_id and dl.M_LANGUAGE_CODE = 'VI'
   
WHERE
    	district.delete_by IS NULL
   	AND 
   		UPPER(dl.NAME) LIKE UPPER('%' || TRIM(/*term*/'') || '%')
OFFSET 0 ROWS FETCH NEXT 20 ROWS ONLY