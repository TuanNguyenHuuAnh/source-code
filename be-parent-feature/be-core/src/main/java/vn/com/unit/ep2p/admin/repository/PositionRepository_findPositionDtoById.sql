SELECT
	position.id 		   AS position_id
	, position.code        AS code
	, position.name 	   AS name
	, position.name_abv    AS name_abv
	, position.actived      AS actived
    , position.description AS description
    , position.company_id  AS company_id
FROM
	jca_position position
WHERE
	position.DELETED_ID = 0	
	AND position.id = /*id*/