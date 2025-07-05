SELECT
	position.id 	       	 AS position_id
    , position.name   	     AS name
    , position.name_abv      AS name_abv
    , position.description 	 AS description
    , position.CREATED_DATE  AS CREATED_DATE
    , co.name                AS company_name
FROM
	jca_position position
	LEFT JOIN jca_company co ON position.company_id = co.id AND co.DELETED_ID = 0
WHERE
	position.DELETED_ID = 0	
	AND position.company_id = /*companyId*/1
ORDER BY company_name, position.name, position.name_abv