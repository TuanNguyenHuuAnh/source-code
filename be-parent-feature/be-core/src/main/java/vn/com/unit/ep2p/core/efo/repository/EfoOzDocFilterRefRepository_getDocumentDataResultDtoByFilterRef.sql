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
	
	
  ,acc_assigned.FULLNAME 		as ASSIGNED_NAME
  ,acc_assigned.ID 				as ASSIGNED_ID
  
  ,acc_submited.FULLNAME 		as SUBMITTED_NAME
  ,acc_submited.ID 				as SUBMITTED_ID
  ,f_Cc.SUBMITTED_DATE			AS SUBMITTED_DATE
  
  ,constPriority.NAME         	AS PRIORITY_NAME
  ,cd.STATUS_NAME				AS STATUS_NAME
   ,processStatusLang.STATUS_NAME	AS PROCESS_STATUS_NAME
 
  
  ,org.NAME						AS ORG_NAME

  , mainFile.ID         		AS MAIN_FILE_ID
  , mainFile.MAJOR_VERSION    	AS MAIN_FILE_MAJOR_VERSION
  , mainFile.MINOR_VERSION    	AS MAIN_FILE_MINOR_VERSION
  , mainFile.MAIN_FILE_NAME_VIEW         AS MAIN_FILE_NAME_VIEW
FROM
EFO_OZ_DOC_FILTER_REF f_Cc
LEFT JOIN
  EFO_DOC doc
ON
  doc.ID = f_Cc.DOC_ID
LEFT JOIN 
    JPM_PROCESS_INST_ACT act
ON 
    doc.ID = act.reference_id
    AND act.reference_type = 1
LEFT JOIN
  EFO_OZ_DOC_MAIN_FILE mainFile
ON 
  mainFile.DOC_ID = doc.ID AND mainFile.DELETED_ID = 0
LEFT JOIN
  JCA_ACCOUNT acc_assigned
ON
  acc_assigned.ID = f_Cc.RELATED_ID
LEFT JOIN 
  jca_account_org position_assigned 
ON 
  acc_assigned.ID = position_assigned.ACCOUNT_ID 
  AND position_assigned.MAIN_FLAG = 1
LEFT JOIN 
  jca_position pos_assigned 
ON 
  position_assigned.position_id = pos_assigned.ID   
INNER JOIN
  JCA_ACCOUNT acc_submited
ON
  acc_submited.ID = f_Cc.SUBMITTED_ID
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
  act.COMMON_STATUS_ID = cd.STATUS_COMMON_ID AND UPPER(cd.LANG_CODE) = UPPER(/*lang*/)
LEFT JOIN 
  JPM_STATUS_DEPLOY processStatus
ON 
        act.PROCESS_STATUS_ID = processStatus.ID
   LEFT JOIN 
  JPM_STATUS_LANG_DEPLOY processStatusLang
ON 
    processStatus.ID = processStatusLang.STATUS_DEPLOY_ID   AND UPPER(processStatusLang.LANG_CODE) = UPPER(/*lang*/)
LEFT JOIN
  JCA_CONSTANT constPriority
ON
  doc.PRIORITY = constPriority.CODE  AND constPriority.KIND = 'DOC_PRIORITY' AND UPPER(constPriority.LANG_CODE) = UPPER(/*lang*/'EN')
LEFT JOIN
  JCA_ORGANIZATION org
ON
  org.ID = doc.OWNER_ORG_ID
WHERE 
	doc.DELETED_ID = 0 
	AND f_Cc.RELATED_TYPE = /*refType*/'REF_CC'
	AND f_Cc.RELATED_ID = /*accountId*/1
	/*IF efoDocumentFilterSearchDto.companyId !=null*/
  		AND doc.COMPANY_ID =/*efoDocumentFilterSearchDto.companyId*/''
  	/*END*/
  	/*IF efoDocumentFilterSearchDto.fromDate !=null*/
  		AND f_Cc.SUBMITTED_DATE >=/*efoDocumentFilterSearchDto.fromDate*/''
  	/*END*/
  	/*IF efoDocumentFilterSearchDto.toDate !=null*/
  		AND f_Cc.SUBMITTED_DATE <=/*efoDocumentFilterSearchDto.toDate*/''
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
  	/*BEGIN*/
  		AND (
  			/*IF efoDocumentFilterSearchDto.docCode != null && efoDocumentFilterSearchDto.docCode !=''*/
	  			 OR UPPER(doc.DOC_CODE) LIKE concat(concat('%',  UPPER(/*efoDocumentFilterSearchDto.docCode*/'a')), '%') 
		  	/*END*/
		  	/*IF efoDocumentFilterSearchDto.docTitle != null && efoDocumentFilterSearchDto.docTitle !=''*/
			  	OR UPPER(doc.DOC_TITLE) LIKE  concat(concat('%',  UPPER(/*efoDocumentFilterSearchDto.docTitle*/'a')), '%') 
		  	/*END*/
		  	/*IF efoDocumentFilterSearchDto.submittedName != null && efoDocumentFilterSearchDto.submittedName !=''*/
			  	OR UPPER(acc_submited.FULLNAME) LIKE  concat(concat('%',  UPPER(/*efoDocumentFilterSearchDto.submittedName*/'a')), '%') 
		  	/*END*/
  		)
  	/*END*/
/*IF orders != null*/
ORDER BY /*$orders*/doc.CREATED_DATE
-- ELSE ORDER BY doc.CREATED_DATE DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/1 ROWS FETCH NEXT  /*size*/1 ROWS ONLY
  /*END*/
/*END*/
