SELECT
	step.ID		          						AS ID
, step.STEP_NO        							AS STEP_NO
	, stepLang.STEP_NAME   						AS NAME
	, statusLang.STATUS_NAME  					AS STATUS_NAME
	, buttonLang.BUTTON_NAME					AS BUTTON_NAME
	, button.BUTTON_CLASS						AS BUTTON_CLASS
	, buttonStep.OPTION_SAVE_FORM 				AS IS_SAVE
	, buttonStep.OPTION_SAVE_EFORM 				AS IS_SAVE_EFORM
	, buttonStep.OPTION_AUTHENTICATE 	   		AS IS_AUTHENTICATE 
	, buttonStep.OPTION_SHOW_HISTORY 	   		AS DISPLAY_HISTORY_APPROVE
	, buttonStep.OPTION_SIGNED    				AS IS_SIGN 
	, buttonStep.OPTION_FILL_TO_EFORM    		AS FIELD_SIGN 
	, buttonStep.OPTION_EXPORT_PDF		    	AS IS_EXPORT_PDF
    , jFunction.PERMISSION_NAME					AS FUNCTION_NAME
    , step.STATUS_ID 	  						AS STATUS_ID
    , step.STEP_CODE 		  					AS CODE	
    , step.STEP_TYPE 							AS STEP_TYPE
    , step.COMMON_STATUS_CODE					AS COMMON_STATUS_CODE
    , statusCommonLang.STATUS_NAME				AS COMMON_STATUS_NAME
    , step.STEP_KIND							AS STEP_KIND
    --, step.AUTHORITY_COMMENT		  	AS AUTHORITY_COMMENT
FROM
	JPM_STEP step
LEFT JOIN
	JPM_STEP_LANG stepLang
ON
	step.ID = stepLang.STEP_ID
	AND UPPER(stepLang.LANG_CODE) = UPPER(/*lang*/'VI')
LEFT JOIN 
	JPM_STATUS_LANG statusLang
ON
	step.STATUS_ID = statusLang.STATUS_ID
	AND UPPER(statusLang.LANG_CODE) = UPPER(/*lang*/'VI')
LEFT JOIN
	JPM_BUTTON_FOR_STEP buttonStep
ON
	buttonStep.STEP_ID = step.ID
LEFT JOIN
	JPM_BUTTON button
ON
	button.ID = buttonStep.BUTTON_ID
	AND button.DELETED_ID = 0
LEFT JOIN 
	JPM_BUTTON_LANG buttonLang
ON 
	button.ID = buttonLang.BUTTON_ID
	AND UPPER(buttonLang.LANG_CODE) = UPPER(/*lang*/'VI')
LEFT JOIN
	JPM_PERMISSION jFunction
ON
	jFunction.ID = buttonStep.PERMISSION_ID
	AND jFunction.DELETED_ID = 0
LEFT JOIN
	JPM_STATUS_COMMON statusCommon
ON 
	statusCommon.ID = step.COMMON_STATUS_ID
LEFT JOIN 
	JPM_STATUS_COMMON_LANG statusCommonLang
ON 
	statusCommon.ID = statusCommonLang.STATUS_COMMON_ID
	AND UPPER(statusCommonLang.LANG_CODE) = UPPER(/*lang*/)
WHERE 
	step.PROCESS_ID = /*processId*/0
	AND step.DELETED_ID = 0
ORDER BY
	step.STEP_NO
	, button.DISPLAY_ORDER
	, jFunction.PERMISSION_CODE