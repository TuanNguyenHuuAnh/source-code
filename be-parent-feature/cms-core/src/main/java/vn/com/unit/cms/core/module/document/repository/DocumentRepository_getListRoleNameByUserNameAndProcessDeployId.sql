SET NOCOUNT ON;

drop table if exists #ROLE_AUTH
select DISTINCT AUTH.ROLE_ID AS ROLE_ID
INTO #ROLE_AUTH
from JPM_AUTHORITY auth
inner join JPM_PERMISSION_DEPLOY permission
ON(permission.ID = auth.PERMISSION_DEPLOY_ID)
where auth.PROCESS_DEPLOY_ID = /*processDeployId*/80
/*IF permissonCode != null &&  permissonCode != ''*/
AND permission.PERMISSION_CODE = /*permissonCode*/'00001';
/*END*/


SELECT ACC.USERNAME AS USER_NAME
, ACC.EMAIL AS EMAIL
, ACC.ID AS ACCOUNT_ID
FROM #ROLE_AUTH AUTH
INNER JOIN JCA_ROLE_FOR_TEAM ROLE_TEAM
ON(ROLE_TEAM.ROLE_ID = AUTH.ROLE_ID)
INNER JOIN JCA_ACCOUNT_TEAM TEAM_ACC
ON(ROLE_TEAM.TEAM_ID = TEAM_ACC.TEAM_ID)
INNER JOIN JCA_ACCOUNT ACC
ON(ACC.ID = TEAM_ACC.ACCOUNT_ID)
WHERE 1 = 1
/*IF userNameLogin != null &&  userNameLogin != ''*/
AND ACC.USERNAME = /*userNameLogin*/''
/*END*/
/*IF channel == null || channel == ''*/
AND isnull(ACC.CHANNEL, 'AG') = 'AG'
/*END*/
/*IF channel != null && channel == 'AG'*/
AND isnull(ACC.CHANNEL, 'AG') = /*channel*/
/*END*/
/*IF channel != null && channel == 'AD'*/
AND ACC.CHANNEL = /*channel*/
/*END*/
GROUP BY ACC.USERNAME, ACC.EMAIL, ACC.ID


SET NOCOUNT OFF;