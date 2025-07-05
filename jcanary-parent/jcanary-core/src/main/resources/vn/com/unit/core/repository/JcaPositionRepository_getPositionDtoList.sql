SELECT
	  position.ID 	       	 	 AS POSITION_ID
    , position.NAME   	     	 AS POSITION_NAME
    , position.NAME_ABV      	 AS POSITION_NAME_ABV
    , position.DESCRIPTION 	 	 AS DESCRIPTION
    , position.CREATED_DATE 	 AS CREATED_DATE
FROM
	JCA_POSITION position    
WHERE
	position.DELETED_ID = 0	
	
ORDER BY position.NAME, position.NAME_ABV