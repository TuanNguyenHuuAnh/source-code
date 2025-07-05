SELECT COUNT(*) AS totalData
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
	AND notify.NOTIFY_TYPE = /*notifyType*/
	/*END*/

	/*IF searchValues != null && searchValues != ''*/
	AND ( notify.CONTENTS LIKE concat('%',/*searchValues*/'hồ sơ','%') OR notify.NOTIFY_TITLE LIKE concat('%',/*searchValues*/'hồ sơ','%'))
	/*END*/
