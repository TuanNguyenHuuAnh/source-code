SET NOCOUNT ON;

DECLARE @MAX_ID BIGINT = (SELECT MAX(PRO.ID) AS  ID
                           FROM JPM_PROCESS_DEPLOY PRO WITH (NOLOCK)
                           INNER JOIN JPM_BUSINESS BUS
                             ON BUS.ID = PRO.BUSINESS_ID
                             AND BUS.BUSINESS_CODE = /*businessCode*/'BUSINESS_MBAL'
                            )
                            
SELECT
    status.ID                   AS STATUS_ID
    , status.PROCESS_DEPLOY_ID  AS PROCESS_ID
    , status.STATUS_CODE        AS STATUS_CODE
    , status.STATUS_NAME        AS STATUS_NAME
FROM
    JPM_STATUS_DEPLOY status WITH (NOLOCK)
WHERE
    status.DELETED_ID = 0
    AND status.PROCESS_DEPLOY_ID = @MAX_ID
    
SET NOCOUNT OFF;