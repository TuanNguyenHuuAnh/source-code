SET NOCOUNT ON;
SELECT  
      notifyDetail.ID               AS ID
    , notify.NOTIFY_CODE            AS NOTIFY_CODE
    , case when notify.NOTIFY_TITLE = N'Dai-ichi Success' then N'Tiến trình xử lý Hồ sơ YCBH' else notify.NOTIFY_TITLE end AS NOTIFY_TITLE
    , notify.CONTENTS               AS CONTENTS
    , notify.LINK_NOTIFY            AS LINK_NOTIFY
    , notify.IS_SEND_IMMEDIATELY    AS LONGTITUDE
    , notify.IS_ACTIVE              AS IS_ACTIVE
    , notify.APPLICABLE_OBJECT      AS APPLICABLE_OBJECT
    , notify.SEND_DATE      		AS SEND_DATE
    , notify.IS_SEND                AS IS_SEND
    , notify.TERRITORRY             AS TERRITORRY
    , notify.AREA                   AS AREA
    , notify.REGION                 AS REGION
    , notify.OFFICE                 AS OFFICE
    , notify.POSITION               AS POSITION
    , notify.IS_FC                  AS IS_FC
    , notify.NOTIFY_TYPE            AS NOTIFY_TYPE
    , notifyDetail.IS_READ_ALREADY 
    , notifyDetail.IS_LIKE
FROM M_NOTIFYS notify WITH(NOLOCK)
INNER JOIN M_NOTIFYS_APPLICABLE_DETAIL notifyDetail  WITH(NOLOCK)
	ON notifyDetail.NOTIFY_ID = notify.id
	AND notifyDetail.AGENT_CODE = /*agentCode*/'1003146'
	/*IF isLike != null*/
		AND notifyDetail.IS_LIKE = /*isLike*/1 
  	/*END*/
	/*IF isReadAlready != null*/
	AND notifyDetail.IS_READ_ALREADY =/*isReadAlready*/0 
	/*END*/

WHERE  notify.DELETE_DATE IS NULL
	AND notify.IS_SEND = 1
	AND (notifyDetail.CREATE_DATE <= GETDATE() OR notify.IS_SEND_IMMEDIATELY = 1)

	/*IF notifyType != null*/
	AND   notify.NOTIFY_TYPE = /*notifyType*/
	/*END*/

	/*IF searchValues != null && searchValues != ''*/
	AND ( notify.CONTENTS LIKE concat('%',/*searchValues*/'hồ sơ','%') OR notify.NOTIFY_TITLE LIKE concat('%',/*searchValues*/'hồ sơ','%'))
	/*END*/
	
ORDER BY notifyDetail.CREATE_DATE DESC
OFFSET /*offset*/0 ROWS FETCH NEXT /*size*/5 ROWS ONLY

SET NOCOUNT OFF;
