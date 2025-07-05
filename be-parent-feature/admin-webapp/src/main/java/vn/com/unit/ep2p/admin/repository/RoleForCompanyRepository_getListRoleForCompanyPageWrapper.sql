SELECT
    co.id as company_id
    ,co.name as company_name
    ,roleco.role_id as role_id
    ,roleCo.org_id
    ,org.name as org_name
    ,roleCo.IS_ADMIN AS is_admin
    
FROM
	jca_role_for_company roleCo
    INNER JOIN jca_company co ON co.id = roleco.company_id AND co.DELETED_ID = 0
    LEFT JOIN JCA_ORGANIZATION org ON org.id = roleco.org_id AND org.DELETED_ID = 0 
WHERE
    1 = 1
    /*IF roleId != null*/
	AND roleCo.role_id= /*roleId*/1
	/*END*/
    /*IF !isAdmin */
    AND roleco.company_id in /*companyIds*/()
    /*END*/
ORDER BY roleCo.CREATED_DATE DESC
OFFSET /*offsetSQL*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY