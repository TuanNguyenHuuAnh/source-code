SELECT ACC.id                 AS ACCOUNT_ID
		, vw.USERNAME		  AS USER_NAME
		, VW.ROLE_ID		  AS ROLE_ID
		, ACC.EMAIL		      AS EMAIL
FROM VW_AUTHORITY_DETAIL vw
INNER JOIN JCA_ACCOUNT acc
ON ACC.USERNAME = vw.username
WHERE
VW.ACTIVED = 1
/*IF roleCode != null && roleCode != '' && groupCode != null && groupCode != ''*/
AND	(VW.FUNCTION_CODE = /*roleCode*/'CMS#ROLE_ADMIN' OR vw.GROUP_CODE = /*groupCode*/'GROUP_CHECKER')
/*END*/
/*IF roleCode != null && roleCode != '' && (groupCode == null || groupCode == '')*/
AND	VW.FUNCTION_CODE = /*roleCode*/'CMS#ROLE_ADMIN'
/*END*/
/*IF groupCode != null && groupCode != '' && (roleCode == null || roleCode == '')*/
AND	vw.GROUP_CODE = /*groupCode*/'GROUP_CHECKER'
/*END*/
/*IF channel == null || channel == ''*/
AND isnull(acc.CHANNEL, 'AG') = 'AG'
/*END*/
/*IF channel != null && channel == 'AG'*/
AND isnull(acc.CHANNEL, 'AG') = /*channel*/
/*END*/
/*IF channel != null && channel == 'AD'*/
AND acc.CHANNEL = /*channel*/
/*END*/
AND ROLE_ACTIVED = 1
AND acc.ACTIVED = 1
AND acc.DELETED_ID = 0
GROUP BY ACC.id, vw.USERNAME, VW.ROLE_ID, ACC.EMAIL;