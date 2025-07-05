--
-- EfoDocRepository_getDocumentDataResultDtoByMyDocument.sql
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
                jca_rule_setting        t_rule
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
                jca_rule_setting		t_rule
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
                jca_rule_setting        t_rule
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
                jca_rule_setting		t_rule
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
                jca_rule_setting		t_rule
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
                jca_rule_setting		t_rule
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
        rule_setting            t_rule
        LEFT JOIN jca_organization_path   org ON t_rule.org_id = org.descendant_id
    WHERE
        t_rule.org_type = 1
    UNION
    -- get org type = 2 "="
    SELECT
        t_rule.org_id
    FROM
        rule_setting			t_rule
    WHERE
        t_rule.org_type = 2
    UNION
    -- get org type = 3 "<"
    SELECT
        org.descendant_id
    FROM
        rule_setting            t_rule
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
	 doc.ID 					AS ID
	,doc.DOC_SUMMARY 			AS DOC_SUMMARY
	,doc.DOC_TYPE 				AS DOC_TYPE
	,doc.DUE_DATE 				AS DUE_DATE
	,doc.DOC_CODE 				AS DOC_CODE
	,doc.DOC_TITLE 				AS DOC_TITLE
	,doc.DOC_TITLE_S 			AS DOC_TITLE_S
	,doc.PRIORITY 				AS PRIORITY
	,doc.FORM_ID 				AS FORM_ID
	,doc.SUBMITTED_ID 			AS DOC_SUBMITTED_ID
	,doc.SUBMITTED_DATE 		AS DOC_SUBMITTED_DATE
	,doc.SUBMITTED_ORG_ID 		AS DOC_SUBMITTED_ORG_ID
	,doc.SUBMITTED_POSITION_ID 	AS DOC_SUBMITTED_POSITION_ID
	,doc.OWNER_ID 				AS DOC_OWNER_ID
	,doc.OWNER_ORG_ID 			AS DOC_OWNER_ORG_ID
	,doc.OWNER_POSITION_ID 		AS DOC_OWNER_POSITION_ID
	,doc.MAJOR_VERSION 			AS MAJOR_VERSION
	,doc.MINOR_VERSION 			AS MINOR_VERSION
	,doc.SYSTEM_CODE 			AS SYSTEM_CODE
	,doc.APP_CODE 				AS APP_CODE
	,doc.COMPANY_ID 			AS COMPANY_ID
	
	,doc.SUBMITTED_DATE 		AS SUBMITTED_DATE
	
	
  ,acc_assigned.FULLNAME 		as ASSIGNED_NAME
  ,acc_assigned.ID 				as ASSIGNED_ID
  
  ,acc_submited.FULLNAME 		as SUBMITTED_NAME
  ,acc_submited.ID 				as SUBMITTED_ID
  
  ,constPriority.NAME         	AS PRIORITY_NAME
  
  ,cd.STATUS_NAME				AS STATUS_NAME
    ,processStatusLang.STATUS_NAME	AS PROCESS_STATUS_NAME
  
  
  ,org.NAME						AS ORG_NAME
  ,acc_staff.FULLNAME			AS STAFF_NAME
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
               AND doc.owner_id = /*accountId*/'' ) )
 	/*IF efoDocumentFilterSearchDto.companyId !=null*/
  		AND doc.COMPANY_ID =/*efoDocumentFilterSearchDto.companyId*/''
  	/*END*/
  	/*IF efoDocumentFilterSearchDto.fromDate !=null*/
  		AND doc.SUBMITTED_DATE >=/*efoDocumentFilterSearchDto.fromDate*/''
  	/*END*/
  	/*IF efoDocumentFilterSearchDto.toDate !=null*/
  		AND doc.SUBMITTED_DATE <=/*efoDocumentFilterSearchDto.toDate*/''
  	/*END*/
  	/*IF efoDocumentFilterSearchDto.formId !=null*/
  		AND doc.FORM_ID=/*efoDocumentFilterSearchDto.formId*/''
  	/*END*/
  	/*IF efoDocumentFilterSearchDto.priority !=null && efoDocumentFilterSearchDto.priority !=''*/
  		AND doc.PRIORITY =/*efoDocumentFilterSearchDto.priority*/''
  	/*END*/
  	/*IF efoDocumentFilterSearchDto.stautusCode !=null && efoDocumentFilterSearchDto.formId !=null && efoDocumentFilterSearchDto.stautusCode !=''*/
  		AND procStatus.STATUS_CODE =/*efoDocumentFilterSearchDto.stautusCode*/''
  	/*END*/
  	/*IF efoDocumentFilterSearchDto.statusCommons !=null && efoDocumentFilterSearchDto.formId ==null */
  		AND sc.STATUS_CODE IN /*efoDocumentFilterSearchDto.statusCommons*/(1)
  	/*END*/  
  	 /*IF efoDocumentFilterSearchDto.orgId !=null && efoDocumentFilterSearchDto.orgId !=''*/
  		AND doc.OWNER_ORG_ID =/*efoDocumentFilterSearchDto.orgId*/''
  	/*END*/
  	 
  	/*BEGIN*/
  		AND (
  			1=2
  			/*IF efoDocumentFilterSearchDto.docCode != null && efoDocumentFilterSearchDto.docCode !=''*/
	  			OR UPPER(doc.DOC_CODE) LIKE concat(concat('%',  UPPER(/*efoDocumentFilterSearchDto.docCode*/'a')), '%') 
		  	/*END*/
		  	/*IF efoDocumentFilterSearchDto.docTitle != null && efoDocumentFilterSearchDto.docTitle !=''*/
			  	OR UPPER(doc.DOC_TITLE) LIKE  concat(concat('%',  UPPER(/*efoDocumentFilterSearchDto.docTitle*/'a')), '%') 
		  	/*END*/
		  	/*IF efoDocumentFilterSearchDto.submittedName != null && efoDocumentFilterSearchDto.submittedName !=''*/
			  	OR UPPER(acc_staff.FULLNAME) LIKE  concat(concat('%',  UPPER(/*efoDocumentFilterSearchDto.submittedName*/'a')), '%') 
		  	/*END*/
  		)
  	/*END*/
  	
/*IF orders != null && orders != ''*/
ORDER BY /*$orders*/''
-- ELSE ORDER BY doc.ID DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/1 ROWS FETCH NEXT /*size*/1 ROWS ONLY
  /*END*/
/*END*/