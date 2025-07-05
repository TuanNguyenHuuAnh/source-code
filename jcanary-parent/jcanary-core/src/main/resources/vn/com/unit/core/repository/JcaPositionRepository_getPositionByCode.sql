SELECT
	  position.ID
	, position.NAME
	, position.NAME_ABV
	, position.DESCRIPTION
	, position.ACTIVED
	, position.CODE
	, position.DISPLAY_ORDER
	, position.COMPANY_ID
	, position.CREATED_ID
	, position.CREATED_DATE
	, position.UPDATED_ID
	, position.UPDATED_DATE
	, position.DELETED_ID
	, position.DELETED_DATE
FROM
	JCA_POSITION position			
WHERE
	position.DELETED_ID = 0	
	AND position.CODE = /*code*/
	/*IF id != null */
	AND position.ID <> /*id*/
	/*END*/