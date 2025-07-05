WITH TBL_DATA AS (
	   SELECT 
          tbl.id                                                                    AS id
        , tbl.code                                                                  AS code
        , tblLang.title                                                             AS title
        , category.title                                                            AS category_name
        , ISNULL(sta.status_name, (SELECT STATUS_NAME FROM JPM_STATUS_COMMON_LANG WHERE STATUS_COMMON_ID = '1000' AND LANG_CODE = /*searchDto.languageCode*/'VI')) AS status_name
        , ISNULL(sta.status_code, '000')                                            AS status_code
        , tbl.enabled                                                               AS enabled
        , tbl.create_date                                                           AS create_date
        , tbl.create_by                                                             AS create_by
        , tbl.update_date                                                           AS update_date
        , tbl.update_by                                                             AS update_by
        , tblLang.content                                                           AS content
        , tbl.sort                                                                  AS sort
        , tbl.posted_date                                                           AS posted_date
        , tbl.expiration_date                                                       AS expiration_date
    FROM m_faqs tbl WITH (NOLOCK)
    INNER JOIN m_faqs_language tblLang WITH (NOLOCK)
        ON tbl.id = tblLang.m_faqs_id
        
    LEFT JOIN m_faqs_category_language category WITH (NOLOCK)
        ON category.m_faqs_category_id = tbl.m_faqs_category_id 
            AND category.m_language_code = tblLang.m_language_code
            AND category.delete_date is null
            
    LEFT JOIN VW_PENDING_AT pending
        ON pending.DOC_ID = tbl.DOC_ID
        
    LEFT JOIN VW_APPROVED approved
        ON approved.DOC_ID = tbl.DOC_ID
        
    LEFT JOIN JPM_PROCESS_INST_ACT ACT WITH (NOLOCK)
  		ON ACT.REFERENCE_ID = tbl.DOC_ID
  		
	LEFT JOIN JPM_STATUS_COMMON_LANG CLANG WITH (NOLOCK)
	  	ON CLANG.STATUS_COMMON_ID = ACT.COMMON_STATUS_ID
	    AND CLANG.LANG_CODE = tblLang.m_language_code
        
    OUTER APPLY dbo.[FN_GET_STATUS_PROCESS](tbl.DOC_ID, /*searchDto.languageCode*/'VI') STA
    
    WHERE
        tbl.delete_date is null
        AND tbl.enabled = 1
       	AND c.STATUS_CODE = 999
        AND ((TRY_CONVERT(DATE, tbl.POSTED_DATE, 103) <= TRY_CONVERT(DATE, tbl.EXPIRATION_DATE, 103) AND TRY_CONVERT(DATE, tbl.EXPIRATION_DATE, 103) > TRY_CONVERT(DATE, GETDATE(), 103) )
        OR tbl.EXPIRATION_DATE is null)
        AND UPPER(tblLang.m_language_code) = UPPER(/*searchDto.languageCode*/'VI')
        
        /*IF searchDto.username != null && searchDto.username != ''*/
            AND (tbl.CREATE_BY = /*searchDto.username*/''
                OR CHARINDEX(CONCAT(', ', /*searchDto.username*/'', ', ') , CONCAT(', ', approved.USERNAME_PENDING, ', ') ) > 0 
                OR CHARINDEX(CONCAT(', ', /*searchDto.username*/'', ', ') , CONCAT(', ', pending.USERNAME_PENDING, ', ') ) > 0 
            )
        /*END*/
)
SELECT *
FROM TBL_DATA TBL
WHERE
	1 = 1
	
	/*IF searchDto.querySearch != NULL && searchDto.querySearch != ''*/
	AND
	(
		/*$searchDto.querySearch*/
	)
	/*END*/
	
/*IF orders != null*/
ORDER BY /*$orders*/tbl.ID
-- ELSE ORDER BY tbl.sort ASC, ISNULL(tbl.update_date, tbl.create_date) DESC, tbl.ENABLED DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/