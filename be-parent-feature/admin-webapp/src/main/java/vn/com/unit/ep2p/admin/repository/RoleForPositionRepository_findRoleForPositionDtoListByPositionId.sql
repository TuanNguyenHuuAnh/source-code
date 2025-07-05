SELECT
     /*positionId*/ AS position_id
    , role.id        AS role_id
    , role.name      AS role_name
    , CASE WHEN role_position.position_id IS NOT NULL
      THEN 1
      ELSE 0
      END            AS checked
FROM
    JCA_ROLE role
LEFT JOIN jca_company co ON role.company_id = co.id AND co.DELETED_ID = 0
LEFT JOIN
    jca_role_for_position role_position
ON
    role.id = role_position.role_id
    AND role_position.position_id = /*positionId*/
WHERE
    role.DELETED_ID = 0
    AND role.ACTIVED = 1
	/*IF companyId != null*/
	AND role.company_id = /*companyId*/
	/*END*/
ORDER BY
	role.NAME