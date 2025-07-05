SELECT
    roleCo.id as id
    ,co.id as company_id
    ,co.name as company_name
    ,roleco.role_id as role_id
    , CASE WHEN roleCo.id IS NOT NULL
      THEN 1
      ELSE 0
      END            AS flg_checked
    ,roleCo.org_id
    ,org.name as org_name
FROM
	JCA_ROLE_FOR_DISPLAY_EMAIL roleCo
    INNER JOIN jca_company co ON co.id = roleco.company_id AND co.DELETED_ID = 0
    LEFT JOIN JCA_ORGANIZATION org ON org.id = roleco.org_id AND org.DELETED_ID = 0 
WHERE
    co.ACTIVED = 1
    /*IF roleId != null*/
	AND roleCo.role_id= /*roleId*/1
	/*END*/
    /*IF !isAdmin */
    AND roleco.company_id in /*companyIds*/()
    /*END*/
ORDER BY roleCo.CREATED_DATE DESC