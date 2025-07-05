SELECT
    chat.*
FROM
    M_ROLE_FOR_CHAT chat
WHERE
    chat.delete_date is NULL
    AND chat.username = /*username*/
    AND UPPER(chat.role_code)  = UPPER(/*role*/)