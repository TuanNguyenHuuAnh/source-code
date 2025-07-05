SELECT
    tb1.id                              AS  "id"
    , tb1.clientid                      AS  "clientid"
    , tb1.email                         AS  "email"
    , tb1.fullname                      AS  "fullname"
    , tb1.phone                         AS  "phone"
    , tb2.fullname                      AS  "agent_name"
    , tb2.username                      AS  "agent"
    , tb1.status                        AS  "status"
    , tb1.created_date                  AS  "created_date"
    , tb1.disconnected_date             AS  "disconnected_date"
    , tb1.disconnected_date             AS  "time_send"
    , (CASE 
        WHEN tb4.created_date - tb1.created_date > 0 THEN CAST(tb4.created_date - tb1.created_date as char(50)) 
       ELSE
           CAST(tb1.created_date - tb4.created_date as char(50))
       END)                             AS  "total_wait"
      , CAST(tb1.disconnected_date - tb1.created_date as char(50)) as total_support
    , tb1.type_disconnected             AS  "type_disconnected"
    , tb5.fullname                      AS  "assign_name"
FROM m_room_client tb1
LEFT JOIN jca_m_account tb2 
    ON tb1.agent = tb2.username 
LEFT JOIN jca_m_account tb5 
    ON tb1.assign = tb5.username 
INNER JOIN (SELECT clientid,min(created_date) as created_date
            FROM m_message
            GROUP BY clientid )  tb4 
    ON tb1.clientid  = tb4.clientid
WHERE
    tb1.created_date IS NOT NULL
    /*IF agent != null && agent != ''*/
    AND tb1.agent  = /*agent*/
    /*END*/
    /*IF dateSearch != null*/
    AND TRUNC(tb1.created_date) = TRUNC(/*dateSearch*/)
    /*END*/
ORDER BY tb1.created_date DESC, NLSSORT(tb5.fullname,'NLS_SORT=generic_m') ASC