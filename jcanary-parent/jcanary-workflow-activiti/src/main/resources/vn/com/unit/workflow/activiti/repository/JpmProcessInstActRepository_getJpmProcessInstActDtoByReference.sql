SELECT
	 procInst.id
	,procInst.process_deploy_id
	,procInst.business_id
	,procInst.process_inst_act_id
	,procInst.reference_id
	,procInst.reference_type
	,procInst.process_status_id
	,procInst.common_status_id
	,procInst.created_date
	,procInst.created_id
	,procInst.updated_date
	,procInst.updated_id
  
  ,statusCom.Status_code	AS COMMON_STATUS_CODE
  ,statusProc.Status_code	AS PROCESS_STATUS_CODE
  
FROM
    jpm_process_inst_act procInst
LEFT JOIN
  JPM_STATUS_COMMON statusCom
ON 
  procInst.COMMON_STATUS_ID = statusCom.ID
LEFT JOIN
  JPM_STATUS_DEPLOY statusProc
ON 
  procInst.PROCESS_STATUS_ID = statusProc.ID
WHERE
    reference_id = /*refId*/''
    AND reference_type = /*refType*/''