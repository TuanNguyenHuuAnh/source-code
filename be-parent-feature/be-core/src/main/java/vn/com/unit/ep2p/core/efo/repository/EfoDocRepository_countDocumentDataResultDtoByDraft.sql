SELECT COUNT(*)
FROM ERS_CANDIDATE FORM WITH (NOLOCK)
WHERE
    FORM.CREATED_BY = /*efoDocumentFilterSearchDto.actionUser*/'linhmtt'
    AND FORM.FORM_ID IS NULL
    AND FORM.CHANNEL = /*efoDocumentFilterSearchDto.channel*/'BANCAS'
    /*IF efoDocumentFilterSearchDto.fromDate !=null*/
    AND FORM.CREATED_DATE >= /*efoDocumentFilterSearchDto.fromDate*/'2021-01-01'
    /*END*/
    /*IF efoDocumentFilterSearchDto.toDate !=null*/
    AND FORM.CREATED_DATE <= /*efoDocumentFilterSearchDto.toDate*/''
    /*END*/