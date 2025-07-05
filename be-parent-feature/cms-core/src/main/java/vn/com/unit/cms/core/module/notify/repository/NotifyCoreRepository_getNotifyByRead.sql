SELECT 
    COUNT(*) AS Total_Message_Unread
FROM M_NOTIFYS notify
INNER JOIN M_NOTIFYS_APPLICABLE_DETAIL notifyDetail
	ON notify.id = notifyDetail.NOTIFY_ID
	AND notifyDetail.AGENT_CODE =/*agentCode*/
	AND notifyDetail.IS_READ_ALREADY = 0
WHERE notify.DELETE_DATE IS NULL
	AND ( notifyDetail.CREATE_DATE <= GETDATE() OR notify.IS_SEND_IMMEDIATELY = 1)
	AND notify.IS_SEND = 1