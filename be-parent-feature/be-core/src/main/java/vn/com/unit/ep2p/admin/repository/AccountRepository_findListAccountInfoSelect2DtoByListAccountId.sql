SELECT
	acc.ID 																			AS ID
	, CONCAT(acc.FULLNAME, CONCAT(' (', CONCAT(acc.EMAIL,')')))						AS TEXT
	, acc.FULLNAME 																	AS NAME
	, acc_pos.POSITION_NAME_MERGE													AS POSITION_NAME
	, acc_org.ORG_NAME_MERGE                                                       	AS DEPARTMENT_NAME
FROM
	jca_account acc
INNER JOIN VW_GET_ORG_FOR_ACCOUNT acc_org ON acc.ID = acc_org.ACCOUNT_ID and acc_org.RN = 1
INNER JOIN VW_GET_POSITION_FOR_ACCOUNT acc_pos ON acc.ID = acc_pos.ACCOUNT_ID and acc_pos.RN = 1
WHERE
	acc.DELETED_ID = 0
	AND acc.ID IN /*listAccountId*/()
	/*IF checkPushNotification */
	AND acc.PUSH_NOTIFICATION = 1
	/*END*/
	/*IF checkPushEmail */
	AND acc.PUSH_EMAIL = 1
	/*END*/
ORDER BY
	acc.USERNAME
	, acc.EMAIL