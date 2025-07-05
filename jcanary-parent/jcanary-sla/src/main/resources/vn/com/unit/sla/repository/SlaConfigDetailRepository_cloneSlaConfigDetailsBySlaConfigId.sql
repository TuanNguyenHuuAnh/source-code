INSERT INTO  SLA_CONFIG_DETAIL (
	SLA_CONFIG_ID
	, ALERT_TYPE
	, ALERT_TIME
	, ALERT_UNIT_TIME
	, TEMPLATE_ID
	, USER_TO
	, USER_CC
	, USER_BCC
	, NOTI_TITLE
	, NOTI_DESCRIPTION
	, EMAIL_SUBJECT
	, EMAIL_CONTENT
	, SEND_MAIL_FLAG
	, SEND_NOTI_FLAG
	, ACTIVE_FLAG
	, CREATED_ID
	, CREATED_DATE
	, UPDATED_ID
	, UPDATED_DATE )  
SELECT  
  /*newSlaConfigId*/
	,sla_detail.ALERT_TYPE
	,sla_detail.ALERT_TIME
	,sla_detail.ALERT_UNIT_TIME
	,sla_detail.TEMPLATE_ID	
	,sla_detail.USER_TO
	,sla_detail.USER_CC
	,sla_detail.USER_BCC
	,sla_detail.NOTI_TITLE
	,sla_detail.NOTI_DESCRIPTION
	,sla_detail.EMAIL_SUBJECT
	,sla_detail.EMAIL_CONTENT
	,sla_detail.SEND_MAIL_FLAG
	,sla_detail.SEND_NOTI_FLAG
	,sla_detail.ACTIVE_FLAG	
  ,/*userId*/
  ,/*sysDate*/
    ,/*userId*/
  ,/*sysDate*/
FROM 
	SLA_CONFIG_DETAIL sla_detail
WHERE 
	sla_detail.SLA_CONFIG_ID = /*oldSlaConfigId*/
	AND sla_detail.DELETED_ID = 0
