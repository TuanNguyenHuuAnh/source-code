--
-- SlaConfigAlertToRepository_cloneSlaConfigAlertTosBySlaConfigDetailId.sql

INSERT INTO SLA_CONFIG_ALERT_TO (SLA_CONFIG_DETAIL_ID, INVOLED_TYPE, RECEIVER_TYPE, RECEIVER_ID, CREATED_ID, CREATED_DATE, UPDATED_ID, UPDATED_DATE)  
SELECT  
  	/*newSlaConfigDetailId*/
	,sla_alert.INVOLED_TYPE
	,sla_alert.RECEIVER_TYPE
	,sla_alert.RECEIVER_ID
  	,/*userId*/
  	,/*sysDate*/
    ,/*userId*/
  	,/*sysDate*/
FROM 
	SLA_CONFIG_ALERT_TO sla_alert
WHERE 
	sla_alert.SLA_CONFIG_DETAIL_ID = /*oldSlaConfigDetailId*/
	-- AND sla_alert.DELETED_ID = 0
