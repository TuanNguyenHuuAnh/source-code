SELECT count(1)
FROM EFO_OZ_DOC_FILTER_IN f_in WITH (NOLOCK)
LEFT JOIN JPM_HI_TASK_ASSIGNEE auth WITH (NOLOCK)
    ON f_in.task_id = auth.task_id
    AND auth.ASSIGNEE_FLAG = 1
LEFT JOIN EFO_DOC doc WITH (NOLOCK)
    ON doc.ID = f_in.DOC_ID
LEFT JOIN JPM_PROCESS_INST_ACT act WITH (NOLOCK)
    ON doc.ID = act.reference_id
    AND act.reference_type = 1
LEFT JOIN JPM_STATUS_COMMON sc WITH (NOLOCK)
    ON act.COMMON_STATUS_ID = sc.ID
LEFT JOIN JPM_STATUS_DEPLOY procStatus WITH (NOLOCK)
    ON act.PROCESS_STATUS_ID = procStatus.ID 
LEFT JOIN JCA_ACCOUNT acc_submited WITH (NOLOCK)
    ON acc_submited.ID = f_in.SUBMITTED_ID
INNER JOIN ERS_CANDIDATE FORM WITH (NOLOCK)
    ON FORM.FORM_ID = f_in.DOC_ID
    AND FORM.CHANNEL = /*efoDocumentFilterSearchDto.channel*/'BANCAS'
WHERE 
    doc.DELETED_ID = 0 
    AND auth.ACCOUNT_ID = /*accountId*/1

    /*IF efoDocumentFilterSearchDto.fromDate !=null*/
    AND f_in.SUBMITTED_DATE >= /*efoDocumentFilterSearchDto.fromDate*/'2021-01-01'
    /*END*/

    /*IF efoDocumentFilterSearchDto.toDate !=null*/
    AND f_in.SUBMITTED_DATE <= /*efoDocumentFilterSearchDto.toDate*/''
    /*END*/

    /*IF efoDocumentFilterSearchDto.formId !=null*/
    AND doc.FORM_ID = /*efoDocumentFilterSearchDto.formId*/''
    /*END*/

    /*IF efoDocumentFilterSearchDto.priority !=null && efoDocumentFilterSearchDto.priority !=''*/
    AND doc.PRIORITY = /*efoDocumentFilterSearchDto.priority*/''
    /*END*/

    /*IF efoDocumentFilterSearchDto.stautusCode !=null && efoDocumentFilterSearchDto.formId !=null && efoDocumentFilterSearchDto.stautusCode !=''*/
    AND procStatus.STATUS_CODE = /*efoDocumentFilterSearchDto.stautusCode*/''
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