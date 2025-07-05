SET NOCOUNT ON;

	DROP TABLE IF EXISTS #todo;
	DROP TABLE IF EXISTS #stepDraft;
	DROP TABLE IF EXISTS #task;
	DROP TABLE IF EXISTS #ROLE_FOR_USER;

    SELECT 
      doc.ID
	  , doc.COMPANY_ID
      , procInst.PROCESS_DEPLOY_ID      AS PROCESS_DEPLOY_ID
      , procInst.BUSINESS_ID
      , procInst.PROCESS_INST_ACT_ID
      , procInst.REFERENCE_ID
      , procInst.REFERENCE_TYPE
      , procInst.PROCESS_STATUS_ID
      , processStatus.STATUS_CODE		AS PROCESS_STATUS_CODE
      , procInst.COMMON_STATUS_ID
      , commonStatus.STATUS_CODE		AS COMMON_STATUS_CODE
	INTO #todo
	FROM EFO_DOC doc
    LEFT JOIN JPM_PROCESS_INST_ACT procInst
		ON procInst.REFERENCE_ID = doc.ID AND procInst.REFERENCE_TYPE = 1
    LEFT JOIN JPM_STATUS_COMMON commonStatus
		ON procInst.COMMON_STATUS_ID = commonStatus.ID
    LEFT JOIN JPM_STATUS_DEPLOY processStatus
		ON procInst.COMMON_STATUS_ID = processStatus.ID
    WHERE 
        doc.ID IN /*efoOzDocIds*/(1679);

	--SELECT * FROM #todo;

    SELECT 
        NULL								AS ACT_TASK_ID
        , step.STEP_CODE					AS STEP_CODE
        , 0									AS ACTIVE
        , step.ID							AS STEP_ID
        , todo.ID							AS DOC_ID 
        , todo.BUSINESS_ID					AS BUSINESS_ID
        , NULL								AS TASK_ID
        , pro.ID							AS PROCESS_DEPLOY_ID
        , todo.COMMON_STATUS_CODE			AS DOC_STATE
        , row_number() over(PARTITION by todo.ID order by pro.EFFECTIVE_DATE DESC, pro.ID DESC) RN
    INTO #stepDraft
	FROM #todo todo
    INNER JOIN JPM_PROCESS_DEPLOY pro ON 
        todo.BUSINESS_ID = pro.BUSINESS_ID 
        AND pro.COMPANY_ID  = todo.COMPANY_ID
        AND pro.DELETED_ID = 0
        AND pro.EFFECTIVE_DATE < /*sysDate*/
    INNER JOIN JPM_STEP_DEPLOY step ON
        pro.ID = step.PROCESS_DEPLOY_ID
        AND step.STEP_NO = 1
        AND step.DELETED_ID = 0
    WHERE todo.COMMON_STATUS_CODE = '000';

	--SELECT * FROM #stepDraft;

	WITH actTask AS (
		  SELECT 
			task.*
			, assignee.ACCOUNT_ID   ACT_USER_ID  
		  FROM JPM_TASK task
		  LEFT JOIN JPM_TASK_ASSIGNEE   assignee 
			ON task.ID = assignee.TASK_ID
		  WHERE
		   task.DOC_ID IN /*efoOzDocIds*/(1679)
		   AND assignee.ASSIGNEE_FLAG = 1
	)
	, task AS(
		SELECT 
			task.CORE_TASK_ID					AS CORE_TASK_ID
			, task.STEP_DEPLOY_CODE				AS STEP_DEPLOY_CODE
			, task.STEP_DEPLOY_ID				AS STEP_DEPLOY_ID
			, task.DOC_ID						AS DOC_ID 
			, todo.BUSINESS_ID					AS BUSINESS_ID
			, task.ID							AS TASK_ID
			, task.PROCESS_DEPLOY_ID			AS PROCESS_DEPLOY_ID
			, todo.COMMON_STATUS_CODE			AS COMMON_STATUS_CODE
		FROM actTask task
		LEFT JOIN #todo todo 
			ON task.DOC_ID = todo.ID AND ACT_USER_ID = /*accountId*/10016
		WHERE todo.COMMON_STATUS_CODE <> '000' 
   
		UNION ALL
    
		SELECT 
			NULL								AS CORE_TASK_ID
			, stepDraft.STEP_CODE				AS STEP_DEPLOY_CODE
			, stepDraft.STEP_ID					AS STEP_DEPLOY_ID
			, stepDraft.DOC_ID					AS DOC_ID 
			, stepDraft.BUSINESS_ID				AS BUSINESS_ID
			, NULL								AS TASK_ID
			, stepDraft.PROCESS_DEPLOY_ID		AS PROCESS_DEPLOY_ID
			, stepDraft.DOC_STATE				AS COMMON_STATUS_CODE
		FROM #stepDraft stepDraft where RN = 1
	)
	select * 
	into #task
	from task;

	--SELECT * FROM #task

	SELECT 
		ACC.id
		, vw.USERNAME
		, VW.ROLE_ID
	INTO #ROLE_FOR_USER
	FROM VW_AUTHORITY_DETAIL vw
	INNER JOIN JCA_ACCOUNT acc
		ON ACC.USERNAME = vw.username
		and ACC.id = /*accountId*/10016
	WHERE
		VW.ACTIVED = 1
		AND ROLE_ACTIVED = 1
	GROUP BY ACC.id, vw.USERNAME, VW.ROLE_ID;

	WITH TBL_DATA AS (
		SELECT DISTINCT
			task.DOC_ID								AS DOC_ID 
			, task.STEP_DEPLOY_ID
			, task.CORE_TASK_ID
			, task.STEP_DEPLOY_CODE					AS STEP_DEPLOY_CODE
			, task.PROCESS_DEPLOY_ID				AS PROCESS_DEPLOY_ID
			, btn.ID                      			AS ID
			, btn.BUTTON_CODE               		AS BUTTON_CODE
			, CASE WHEN btnLang.BUTTON_NAME IS NOT NULL
			  THEN btnLang.BUTTON_NAME 
			  ELSE btn.BUTTON_TEXT END				AS BUTTON_TEXT
			, btn.BUTTON_VALUE						AS BUTTON_VALUE
			, btn.BUTTON_CLASS						AS BUTTON_CLASS
			, btn.BUTTON_TYPE						AS BUTTON_TYPE
			, btnStep.OPTION_SAVE_FORM				AS SAVE_FORM
			, btnStep.OPTION_SAVE_EFORM				AS SAVE_EFORM
			, btnStep.PERMISSION_CODE				AS PERMISSION_CODE
			, step.ID								AS STEP_ID
			, btnStep.OPTION_AUTHENTICATE			AS AUTHENTICATE
			, btnStep.OPTION_SIGNED					AS SIGNED
			, btnStep.OPTION_EXPORT_PDF				AS EXPORT_PDF
			, btn.DISPLAY_ORDER						AS DISPLAY_ORDER
			, btnLang.BUTTON_NAME_IN_PASSIVE		AS BUTTON_NAME_PASSIVE
			, step.USE_CLAIM_BUTTON					AS USE_CLAIM_BUTTON   
			, btnStep.PERMISSION_DEPLOY_ID
		FROM #task task
		LEFT JOIN JPM_BUTTON_FOR_STEP_DEPLOY btnStep 
			ON task.STEP_DEPLOY_ID = btnStep.STEP_DEPLOY_ID
		LEFT JOIN JPM_BUTTON_DEPLOY btn
			ON btnStep.BUTTON_DEPLOY_ID = btn.ID
		LEFT JOIN JPM_STEP_DEPLOY step
			ON btnStep.STEP_DEPLOY_ID = step.Id
		LEFT JOIN JPM_BUTTON_LANG_DEPLOY btnLang
			ON btn.ID = btnLang.BUTTON_DEPLOY_ID
			AND btnLang.LANG_CODE = /*lang*/'VI'
		INNER JOIN JPM_AUTHORITY authJpm
			ON authJpm.PERMISSION_DEPLOY_ID = btnStep.PERMISSION_DEPLOY_ID
		INNER JOIN #ROLE_FOR_USER roleAccount
			ON roleAccount.ROLE_ID = authJpm.ROLE_ID
	)
	SELECT * FROM TBL_DATA btn
	ORDER BY
		btn.DISPLAY_ORDER
		, btn.BUTTON_TEXT

SET NOCOUNT OFF;