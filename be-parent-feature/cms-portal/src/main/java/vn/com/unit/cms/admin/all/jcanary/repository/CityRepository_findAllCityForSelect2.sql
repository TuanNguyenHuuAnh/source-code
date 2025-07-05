SELECT
	distinct(TRIM(cl.name)) as id,
    cl.name as text,
    cl.name as name
FROM
    jca_m_city city
    LEFT JOIN jca_m_city_language cl on city.id = cl.m_city_id and cl.M_LANGUAGE_CODE = 'VI'
   
WHERE
    	city.delete_by IS NULL
   	AND 
   		UPPER(cl.NAME) LIKE UPPER('%' || TRIM(/*term*/'') || '%')
OFFSET 0 ROWS FETCH NEXT 20 ROWS ONLY