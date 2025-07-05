SELECT
	appInbox.ID
FROM
  JCA_APP_INBOX appInbox
WHERE
appInbox.read_flag = 1
AND
(
    (appInbox.UPDATED_DATE is null and /*sysDate*/ > TRUNC(appInbox.CREATED_DATE + /*dateSum*/1))
    OR
    (appInbox.UPDATED_DATE is not null and /*sysDate*/ > TRUNC(appInbox.UPDATED_DATE + /*dateSum*/1))
        OR
    (appInbox.DELETED_BY is not null )
)
ORDER BY appInbox.ID DESC 
OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY