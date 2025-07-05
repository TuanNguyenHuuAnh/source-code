SELECT
	*,
	ID AS USER_ID
FROM
	jca_account acc
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