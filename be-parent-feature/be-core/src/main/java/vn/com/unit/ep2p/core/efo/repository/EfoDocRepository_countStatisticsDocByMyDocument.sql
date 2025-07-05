-- EfoDocRepository_countStatisticsDocByMyDocument.sql

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
                jca_rule_setting t_rule
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
                jca_rule_setting    t_rule
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
                jca_rule_setting t_rule
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
                jca_rule_setting    t_rule
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
        rule_setting t_rule
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
        jca_rule_exception t_rule
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
)
,doc_for_acc (
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

, DOC AS (
	SELECT
	    doc.ID
	    ,commonStatus.STATUS_CODE AS DOC_STATE	
	    FROM
	    	efo_doc doc
	    LEFT JOIN 
	    	JPM_PROCESS_INST_ACT procInst
	    ON 
	        doc.ID = procInst.reference_id
	        AND procInst.reference_type = 1
	    LEFT JOIN 
      		JPM_STATUS_COMMON commonStatus
    	ON 
        	procInst.COMMON_STATUS_ID = commonStatus.ID
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
	WHERE
	    doc.DELETED_ID = 0
	    AND ( tree.org_id IS NOT NULL
	          OR tree_ex.org_id IS NOT NULL
	          OR doc_acc.doc_id IS NOT NULL
	          OR ( commonStatus.status_code = '000'
	               AND doc.owner_id = /*accountId*/1 ) )
	 	/*IF efoOzDocStatisticsSearchDto.companyId !=null*/
	  		AND doc.COMPANY_ID =/*efoOzDocStatisticsSearchDto.companyId*/''
	  	/*END*/
	  	/*IF efoOzDocStatisticsSearchDto.fromDate !=null*/
	  		AND doc.CREATED_DATE >=/*efoOzDocStatisticsSearchDto.fromDate*/''
	  	/*END*/
	  	/*IF efoOzDocStatisticsSearchDto.toDate !=null*/
	  		AND doc.CREATED_DATE <=/*efoOzDocStatisticsSearchDto.toDate*/''
	  	/*END*/
	  	/*IF efoOzDocStatisticsSearchDto.formId !=null*/
	  		AND doc.FORM_ID=/*efoOzDocStatisticsSearchDto.formId*/''
	  	/*END*/
	  	/*IF efoOzDocStatisticsSearchDto.orgId !=null && efoOzDocStatisticsSearchDto.orgId !=''*/
	  		AND doc.OWNER_ORG_ID =/*efoOzDocStatisticsSearchDto.orgId*/''
	  	/*END*/
	  	/*BEGIN*/
	  		AND (
	  			/*IF efoOzDocStatisticsSearchDto.docCode != null && efoOzDocStatisticsSearchDto.docCode !=''*/
		  			 OR UPPER(doc.DOC_CODE) LIKE concat(concat('%',  UPPER(/*efoOzDocStatisticsSearchDto.docCode*/'a')), '%') 
			  	/*END*/
			  	/*IF efoOzDocStatisticsSearchDto.docTitle != null && efoOzDocStatisticsSearchDto.docTitle !=''*/
				  	OR UPPER(doc.DOC_TITLE) LIKE  concat(concat('%',  UPPER(/*efoOzDocStatisticsSearchDto.docTitle*/'a')), '%') 
			  	/*END*/
	  		)
	  	/*END*/
	),
UN_APPROVED_DOC AS (
	SELECT COUNT(ID) AS UN_APPROVED_DOC FROM DOC 
	/*BEGIN*/
	WHERE 
		/*IF efoOzDocStatisticsSearchDto.lstUnApprovedDocCat != null */
		DOC_STATE IN /*efoOzDocStatisticsSearchDto.lstUnApprovedDocCat*/()
		/*END*/
										
	/*END*/
		
),

REJECTED_DOC AS (
	SELECT COUNT(1) AS REJECTED_DOC FROM DOC 
	/*BEGIN*/
	WHERE 
		/*IF efoOzDocStatisticsSearchDto.lstRejectedDocCat != null */
		DOC_STATE IN /*efoOzDocStatisticsSearchDto.lstRejectedDocCat*/()
		/*END*/
										
	/*END*/
		
),

ALL_DOC AS (
	SELECT COUNT(1) AS ALL_DOC FROM DOC 
),

APPROVED_DOC AS (
	SELECT COUNT(ID) AS APPROVED_DOC FROM DOC 
	/*BEGIN*/
	WHERE 
		/*IF efoOzDocStatisticsSearchDto.lstApprovedDocCat != null */
		DOC_STATE IN /*efoOzDocStatisticsSearchDto.lstApprovedDocCat*/()
		/*END*/
										
	/*END*/
		
)

SELECT 	UN_APPROVED_DOC.*
		, REJECTED_DOC.*
		, APPROVED_DOC.*
		,ALL_DOC.*
FROM 
		UN_APPROVED_DOC
		,REJECTED_DOC
		,APPROVED_DOC
		,ALL_DOC	  	