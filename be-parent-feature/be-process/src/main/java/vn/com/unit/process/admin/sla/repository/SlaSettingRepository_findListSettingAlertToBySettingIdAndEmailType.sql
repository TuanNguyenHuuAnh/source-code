SELECT  
	cona.ID                 			AS ID
	, cona.SLA_CONFIG_DETAIL_ID			AS SLA_CONFIG_DETAIL_ID
    , cona.INVOLED_TYPE					AS INVOLED_TYPE
    , cona.RECEIVER_ID              	AS RECEIVER_ID
    , cona.RECEIVER_TYPE                AS RECEIVER_TYPE
FROM 
	SLA_CONFIG_ALERT_TO cona
WHERE
	cona.SLA_CONFIG_DETAIL_ID = /*settingId*/ 9