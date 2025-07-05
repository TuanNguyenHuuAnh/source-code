
	select btn_dep.id as BUTTON_ID, btn_dep.BUTTON_TEXT, dep.BUSINESS_ID, dep.COMPANY_ID, doc.DOC_CODE as DOC_CODE, doc.ID AS DOC_ID, doc.ID AS FORM_ID
	, DOC.DOC_SUMMARY, DOC.DOC_TITLE, task.CREATED_ID AS owner_id, '1' as priority
	, task.PROCESS_DEPLOY_ID, '3' as PROCESS_TYPE, task.CORE_TASK_ID
	 from EFO_DOC doc
	left join JPM_TASK task
	on(doc.ID = task.DOC_ID)
	left join JPM_PROCESS_DEPLOY dep
	on(task.PROCESS_DEPLOY_ID = dep.ID)
	left join JPM_BUTTON_FOR_STEP_DEPLOY btn
	on(btn.STEP_DEPLOY_ID = task.STEP_DEPLOY_ID)
	left join JPM_BUTTON_DEPLOY btn_dep
	on(btn_dep.ID = btn.BUTTON_DEPLOY_ID)
	where doc.ID = /*docId*/316
	and btn_dep.BUTTON_TEXT = /*buttonText*/'Update'
	/*IF stepCode != null && stepCode != ''*/
	and btn.STEP_CODE = /*stepCode*/''
	/*END*/