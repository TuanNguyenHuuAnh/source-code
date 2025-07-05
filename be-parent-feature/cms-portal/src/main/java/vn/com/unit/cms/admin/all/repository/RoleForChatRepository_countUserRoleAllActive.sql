SELECT 
    COUNT(DISTINCT chat.ROLE_CODE)
FROM
    M_ROLE_FOR_CHAT chat
LEFT JOIN jca_m_account account
	ON (account.delete_date is NULL
		AND account.username = chat.username
	)
WHERE
    chat.delete_date is NULL
	/*BEGIN*/ AND (
		/*IF userDto.roleName != null && userDto.roleName != ''*/
		OR UPPER(chat.role_code) LIKE ('%'|| UPPER(/*userDto.roleName*/) ||'%')
		/*END*/
		/*IF userDto.user != null && userDto.user != ''*/
		OR UPPER(account.fullname) LIKE ('%'|| UPPER(/*userDto.user*/) ||'%')
		/*END*/
	) /*END*/