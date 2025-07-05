SELECT org.*
	   ,main.ANCESTOR_ID			as PARENT_ORG_ID 
FROM
    JCA_ORGANIZATION_PATH       main
    LEFT JOIN JCA_ORGANIZATION            org 
        ON main.descendant_id = org.id
WHERE
    DELETED_ID = 0
    /*IF orgType != null && orgType != ''*/
    AND org_type = /*orgType*/''
    /*END*/
    /*IF companyId != null && companyId != ''*/
    AND company_id = /*companyId*/
    /*END*/
   	/*IF parentId != null && parentId != ''*/
    AND ANCESTOR_ID = /*parentId*/
    /*END*/
    AND DEPTH = 1
ORDER BY 
    ANCESTOR_ID
    , DISPLAY_ORDER