SELECT
	position.id 	       	 AS position_id
    , position.name   	     AS name
    , position.name_abv      AS name_abv
    , position.description 	 AS description
    , position.CREATED_DATE 	 AS CREATED_DATE
FROM
	jca_position position    
WHERE
	position.DELETED_ID = 0	
	ORDER BY position.name, position.name_abv