--
-- EfoDocRepository_getDetailDocumentByRole.sql
WITH rule_setting (
    org_id,
    org_type
) AS (
    SELECT
        org.org_id,
        org.rule_org_type
    FROM
        (
            -- check org cua user
            -- get org type = 1 ">"
            SELECT
                t_rule.org_id,
                t_rule.rule_org_type
            FROM
                jca_rule_setting      t_rule
                LEFT JOIN jca_organization_path   org ON t_rule.org_id = org.descendant_id
            WHERE
                t_rule.rule_org_type = 1
                /*IF orgIds != null && orgIds.size() > 0*/
                AND org.ancestor_id IN /*orgIds*/(1)
                --ELSE AND org.ancestor_id = 1
                /*END*/
            UNION
            -- get org type = 2 "="
            SELECT
                t_rule.org_id,
                t_rule.rule_org_type
            FROM
                jca_rule_setting      t_rule
            WHERE
                t_rule.rule_org_type = 2
                /*IF orgIds != null && orgIds.size() > 0*/
                AND t_rule.org_id IN /*orgIds*/(1)
                --ELSE AND t_rule.org_id = 1
                /*END*/
            UNION
            -- get org type = 3 "<"
            SELECT
                t_rule.org_id,
                t_rule.rule_org_type
            FROM
                jca_rule_setting      t_rule
                LEFT JOIN jca_organization_path   org ON t_rule.org_id = org.ancestor_id
            WHERE
                t_rule.rule_org_type = 3
                /*IF orgIds != null && orgIds.size() > 0*/
                AND org.descendant_id IN /*orgIds*/(1)
                --ELSE AND org.descendant_id = 1
                /*END*/
        ) org
        INNER JOIN (
            -- check possition cua user
            -- get possition type = 1 ">"
            SELECT
                t_rule.org_id,
                t_rule.rule_org_type
            FROM
                jca_rule_setting      t_rule
                LEFT JOIN jca_position_path   pos ON t_rule.position_id = pos.descendant_id
            WHERE
                t_rule.rule_position_type = 1
                /*IF positionIds != null && positionIds.size() > 0*/
                AND pos.ancestor_id IN /*positionIds*/(1)
                --ELSE AND pos.ancestor_id = 1
                /*END*/
            UNION
            -- get possition type = 2 "="
            SELECT
                t_rule.org_id,
                t_rule.rule_org_type
            FROM
                jca_rule_setting      t_rule
            WHERE
                t_rule.rule_position_type = 2
                /*IF positionIds != null && positionIds.size() > 0*/
                AND t_rule.position_id IN /*positionIds*/(1)
                --ELSE AND t_rule.position_id = 1
                /*END*/
            UNION
            -- get possition type = 3 "<"
            SELECT
                t_rule.org_id,
                t_rule.rule_org_type
            FROM
                jca_rule_setting      t_rule
                LEFT JOIN jca_position_path   pos ON t_rule.position_id = pos.ancestor_id
            WHERE
                t_rule.rule_position_type = 3
                /*IF positionIds != null && positionIds.size() > 0*/
                AND pos.descendant_id IN /*positionIds*/(1)
                --ELSE AND pos.descendant_id = 1
                /*END*/
        ) pos ON org.org_id = pos.org_id
                 AND org.rule_org_type = pos.rule_org_type
), org_tree ( org_id ) AS (
    -- lay cay org cua rule setting lam dieu kien search cho document
    -- get org type = 1 ">"
    SELECT
        org.ancestor_id
    FROM
        rule_setting      t_rule
        LEFT JOIN jca_organization_path   org ON t_rule.org_id = org.descendant_id
    WHERE
        t_rule.org_type = 1
    UNION
    -- get org type = 2 "="
    SELECT
        t_rule.org_id
    FROM
        rule_setting      t_rule
    WHERE
        t_rule.org_type = 2
    UNION
    -- get org type = 3 "<"
    SELECT
        org.descendant_id
    FROM
        rule_setting      t_rule
        LEFT JOIN jca_organization_path   org ON t_rule.org_id = org.ancestor_id
    WHERE
        t_rule.org_type = 3
), org_tree_ex ( org_id ) AS (
    -- lay cay org cua rule setting lam dieu kien search cho document
    -- get org type = 1 ">"
    SELECT
        org.ancestor_id
    FROM
        jca_rule_exception      t_rule
        LEFT JOIN jca_organization_path   org ON t_rule.org_id = org.descendant_id
    WHERE
        t_rule.rule_org_type = 1
        AND t_rule.account_id = /*accountId*/1
    UNION
    -- get org type = 2 "="
    SELECT
        t_rule.org_id
    FROM
        jca_rule_exception      t_rule
    WHERE
        t_rule.rule_org_type = 2
        AND t_rule.account_id = /*accountId*/1
    UNION
    -- get org type = 3 "<"
    SELECT
        org.descendant_id
    FROM
        jca_rule_exception      t_rule
        LEFT JOIN jca_organization_path   org ON t_rule.org_id = org.ancestor_id
    WHERE
        t_rule.rule_org_type = 3
        AND t_rule.account_id = /*accountId*/1
),doc_for_acc (
    doc_id,
    ASSGINEE_ID,
    SUBMITTER_ID,
    OWNER_ID,
    type
) AS (
-- IN DOCUMENT
    SELECT 
      f_in.DOC_ID	        AS EFO_OZ_DOC_ID,
      auth.ACCOUNT_ID 		AS ASSGINEE_ID,
      f_in.SUBMITTED_ID		AS SUBMITTER_ID,
      f_in.OWNER_ID         AS OWNER_ID,
      1 					AS type
    FROM
      EFO_OZ_DOC_FILTER_IN f_in
      LEFT JOIN 
        JPM_HI_TASK_ASSIGNEE auth
      ON
        f_in.task_id = auth.task_id
     WHERE 
		auth.ACCOUNT_ID = /*accountId*/1
        AND auth.ASSIGNEE_FLAG = 1
-- OUT DOCUMENT
    UNION
    SELECT 
      f_out.DOC_ID		        AS EFO_OZ_DOC_ID,
      f_out.COMPLETED_ID 		AS ASSGINEE_ID,
      f_out.SUBMITTED_ID		AS SUBMITTER_ID,
      f_out.OWNER_ID            AS OWNER_ID,
      2 						AS type
      FROM
        EFO_OZ_DOC_FILTER_OUT f_out
    WHERE 
        f_out.COMPLETED_ID = /*accountId*/1
-- REFERENCE DOCUMENT
    UNION
    SELECT
        f_Cc.DOC_ID	 	        AS EFO_OZ_DOC_ID,
        f_Cc.RELATED_ID      	AS ASSGINEE_ID,
      	f_Cc.SUBMITTED_ID  		AS SUBMITTER_ID,
        f_Cc.OWNER_ID           AS OWNER_ID,
        3 						AS type
    FROM 
        EFO_OZ_DOC_FILTER_REF f_Cc
    WHERE 
      f_Cc.DELETED_ID = 0 
      AND f_Cc.RELATED_TYPE IN /*refTypes*/(1)
      AND f_Cc.RELATED_ID = /*accountId*/1
)
SELECT
   doc.*
  ,org.ID 						as ORG_ID
  ,acc_assigned.FULLNAME 		as ASSIGNED_NAME
  ,acc_assigned.ID 				as ASSIGNED_ID
  
  ,acc_submited.FULLNAME 		as SUBMITTED_NAME
  ,acc_submited.ID 				as SUBMITTED_ID
  
  ,constPriority.NAME         	AS PRIORITY_NAME
  ,cd.STATUS_NAME				AS STATUS_NAME
  ,processStatusLang.STATUS_NAME	AS PROCESS_STATUS_NAME
  
  ,org.NAME						AS ORG_NAME
  ,acc_staff.FULLNAME			AS STAFF_NAME
  
    , mainFile.ID                 	AS MAIN_FILE_ID
  , mainFile.MAJOR_VERSION      	AS MAIN_FILE_MAJOR_VERSION
  , mainFile.MINOR_VERSION      	AS MAIN_FILE_MINOR_VERSION
  , mainFile.MAIN_FILE_NAME_VIEW    AS MAIN_FILE_NAME_VIEW
  
  
   ,act.PROCESS_DEPLOY_ID      		AS PROCESS_DEPLOY_ID
   ,act.BUSINESS_ID
   ,act.PROCESS_INST_ACT_ID
   ,act.PROCESS_STATUS_ID
   ,processStatus.STATUS_CODE     	AS PROCESS_STATUS_CODE
   ,act.COMMON_STATUS_ID
   ,sc.STATUS_CODE     			  	AS COMMON_STATUS_CODE
FROM
    efo_doc         doc
    LEFT JOIN 
    	JPM_PROCESS_INST_ACT act
    ON 
	    doc.ID = act.reference_id
    	AND act.reference_type = 1
    LEFT JOIN 
    	org_tree tree 
    ON 
    	doc.OWNER_ORG_ID = tree.org_id
    LEFT JOIN 
    	org_tree_ex tree_ex 
    ON 
    	doc.OWNER_ORG_ID = tree_ex.org_id
    LEFT JOIN 
    	doc_for_acc doc_acc 
    ON 
    	doc.id = doc_acc.doc_id
	LEFT JOIN
	  EFO_OZ_DOC_MAIN_FILE mainFile
	ON 
	  mainFile.DOC_ID = doc.ID AND mainFile.DELETED_ID = 0
    LEFT JOIN
	  JCA_ACCOUNT acc_staff
	ON
	  acc_staff.ID = doc.OWNER_ID
    LEFT JOIN
	  JCA_ACCOUNT acc_assigned
	ON
	  acc_assigned.ID = doc_acc.ASSGINEE_ID
	LEFT JOIN 
	  jca_account_org position_assigned 
	ON 
	  acc_assigned.ID = position_assigned.ACCOUNT_ID 
	  AND position_assigned.MAIN_FLAG = 1
    LEFT JOIN 
	  jca_position pos_assigned 
	ON 
	  position_assigned.position_id = pos_assigned.ID   
	LEFT JOIN
	  JCA_ACCOUNT acc_submited
	ON
	  acc_submited.ID = doc_acc.SUBMITTER_ID
	LEFT JOIN 
	  jca_account_org position_submited
	ON 
	  acc_assigned.ID = position_submited.ACCOUNT_ID 
	  AND position_submited.MAIN_FLAG = 1
    LEFT JOIN 
	  jca_position pos_submited
	ON 
	  position_assigned.position_id = pos_submited.ID   
    LEFT JOIN JPM_STATUS_COMMON sc
    ON 
        act.COMMON_STATUS_ID = sc.ID
	LEFT JOIN
	  JPM_STATUS_COMMON_LANG cd
	ON
	  act.COMMON_STATUS_ID = cd.STATUS_COMMON_ID AND UPPER(cd.LANG_CODE) = UPPER(/*lang*/'EN')
    LEFT JOIN 
      JPM_STATUS_DEPLOY processStatus
    ON 
        act.PROCESS_STATUS_ID = processStatus.ID
   LEFT JOIN 
      JPM_STATUS_LANG_DEPLOY processStatusLang
    ON 
        processStatus.ID = processStatusLang.STATUS_DEPLOY_ID   AND UPPER(processStatusLang.LANG_CODE) = UPPER(/*lang*/'EN') 
 	LEFT JOIN
	  JCA_CONSTANT constPriority
	ON
	  doc.PRIORITY = constPriority.CODE  AND constPriority.KIND = 'DOC_PRIORITY' AND UPPER(constPriority.LANG_CODE) = UPPER(/*lang*/'EN')
  	LEFT JOIN
      JCA_ORGANIZATION org
    ON
      org.ID = doc.OWNER_ORG_ID
    LEFT JOIN
	  JCA_ACCOUNT acc_staff_org
	ON
	  acc_staff_org.ID = doc.OWNER_ORG_ID	  
WHERE
    doc.DELETED_ID = 0
    AND ( tree.org_id IS NOT NULL
          OR tree_ex.org_id IS NOT NULL
          OR doc_acc.doc_id IS NOT NULL
          OR ( sc.status_code = '000'

               ) )
                   AND doc.ID = /*documentId*/
               