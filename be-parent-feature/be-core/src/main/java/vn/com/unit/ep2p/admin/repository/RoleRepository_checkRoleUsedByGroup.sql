SELECT count(*) 
FROM JCA_ROLE
WHERE
    id IN ( SELECT ROLE_ID FROM JCA_ROLE_for_team where DELETED_ID = 0)
    and id = /*id*/