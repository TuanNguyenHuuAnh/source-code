SELECT
	acc.EMAIL
FROM
	jca_account acc
WHERE
	acc.DELETED_ID = 0
	AND acc.EMAIL IN /*listEmail*/()
	/*IF checkPushEmail */
	AND acc.PUSH_EMAIL = 1
	/*END*/