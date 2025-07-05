SET NOCOUNT ON;

DECLARE @MAX_ID BIGINT = (SELECT
                                                        MAX(PRO.ID) AS  ID
                                                    FROM JPM_PROCESS_DEPLOY PRO WITH (NOLOCK)
                                                    INNER JOIN JPM_BUSINESS BUS
                                                        ON BUS.ID = PRO.BUSINESS_ID
                                                        AND BUS.BUSINESS_CODE = /*businessCode*/'BUSINESS_MBAL'
                                                    )

SELECT 
    statusLang.LANG_ID                              AS LANG_ID
    , statusLang.LANG_CODE                  AS LANG_CODE
    , statusLang.STATUS_NAME                AS STATUS_NAME
    , statusLang.STATUS_DEPLOY_ID       AS STATUS_ID
FROM
    JPM_STATUS_DEPLOY status
LEFT JOIN
    JPM_STATUS_LANG_DEPLOY statusLang
ON
    status.ID = statusLang.STATUS_DEPLOY_ID
WHERE
    status.DELETED_ID = 0
  AND statusLang.STATUS_NAME IS NOT NULL
    AND status.PROCESS_DEPLOY_ID = @MAX_ID;
    
SET NOCOUNT OFF;