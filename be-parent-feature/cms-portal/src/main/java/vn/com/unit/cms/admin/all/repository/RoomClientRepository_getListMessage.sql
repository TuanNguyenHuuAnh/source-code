SELECT
    tb1.messageid                                       AS "messageid"
    , tb1.clientid                                      AS "clientid"
    , tb2.fullname                                      AS "agent_name"
    , tb1.agent                                         AS "agent"
    , tb1.message                                       AS "message"
    , tb1.type                                          AS "type"
    , tb1.type_message                                  AS "type_message"
    , tb1.status                                        AS "status"
    , client.fullname                                   AS "fullname"
    , TO_CHAR(tb1.created_date,'HH24:MI')               AS "time_send"
    , tb1.created_date                                  AS "created_date"
    , tb1.old_agent										AS	"old_agent"
    , tb1.old_agent_name								AS	"old_agent_name"
FROM m_message tb1
LEFT JOIN jca_m_account tb2
    ON (tb1.agent = tb2.username)
LEFT JOIN M_ROOM_CLIENT client
    ON (client.clientid = tb1.CLIENTID)
WHERE
    tb1.clientid = /*clientid*/
ORDER BY tb1.created_date ASC