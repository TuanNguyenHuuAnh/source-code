SELECT
    rolePosition.ID AS ID
    , /*positionId*/1 AS POSITION_ID
    , role.ID        AS ROLE_ID
    , role.NAME      AS ROLE_NAME
    , CASE WHEN rolePosition.ID IS NOT NULL
      THEN 1
      ELSE 0
      END            AS CHECKED
FROM
    JCA_ROLE role
LEFT JOIN JCA_COMPANY co ON role.COMPANY_ID = co.ID AND co.DELETED_ID = 0
LEFT JOIN
    JCA_ROLE_FOR_POSITION rolePosition
ON
    role.ID = rolePosition.ROLE_ID
    AND rolePosition.POSITION_ID = /*positionId*/1
    AND rolePosition.DELETED_ID = 0
WHERE
    role.DELETED_ID = 0
    AND role.ACTIVE = 1
	/*IF companyId != NULL*/
	AND role.COMPANY_ID = /*companyId*/1
	/*END*/
ORDER BY
	role.NAME