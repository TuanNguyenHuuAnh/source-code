SELECT
	ID AS ID
	, Platform AS PLATFORM
	, Version AS VERSION
	, Configuration AS CONFIGURATION
	, Message AS MESSAGE
	, UpdateNote AS UPDATE_NOTE
	, FORMAT(CreatedDate, 'dd/MM/yyyy HH:mm:ss') AS CREATED_DATE
	, FORMAT(UpdatedDate, 'dd/MM/yyyy HH:mm:ss') AS UPDATED_DATE
FROM TB_VERSION v
WHERE 1=1
/*IF platform != null && platform != ''*/
	AND v.Platform = /*platform*/'IOS_mCP'
/*END*/