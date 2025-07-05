SELECT
    roomServer.agent                    AS  username
    , account.fullname                  AS  fullname
    , account.email                     AS  email
    , account.phone                     AS  phone
    , server.created_date               AS  created_date
FROM
    m_room_server roomServer
INNER JOIN JCA_M_ACCOUNT account
    ON(account.username = roomServer.agent
    	AND account.delete_date is NULL
    )
INNER JOIN m_socket_server server
    ON (server.DISCONNECTED_DATE IS NULL
        AND roomServer.agent = server.agent
    )
INNER JOIN m_role_for_chat chat
	ON (chat.delete_date is NULL
		AND chat.username = server.agent
	)
WHERE
    roomServer.status = '0'
    AND TRUNC(roomServer.created_date) = TRUNC(sysdate)
ORDER BY server.created_date DESC