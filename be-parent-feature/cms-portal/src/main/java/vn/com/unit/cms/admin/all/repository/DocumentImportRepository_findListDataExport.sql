SELECT
	 ROW_NUMBER ()  OVER ( ORDER BY docImport.ID ASC )   as no_id
    ,docImport.id                    as id
    ,docImport.CODE                  as code
    ,docImport.TITLE                 as title
    ,docImport.KEYWORDS_DESC         as keywords_desc
    ,docImport.ENABLED               as enabled
    ,docImport.POSTED_DATE           as posted_date
    ,docImport.EXPIRATION_DATE       as expiration_date
    ,docImport.MESSAGE_ERROR         as message_error
    ,docImport.IS_ERROR              as is_error
    ,docImport.DOCUMENT_CODE         as document_code
    ,docCateLang.TITLE               as doc_Cate_Title
    ,docImport.KEYWORDS_SEO          as keywords_seo
    ,docImport.KEYWORDS              as keywords
    ,docImport.PHYSICAL_FILE_NAME	 as PHYSICAL_FILE_NAME
FROM M_DOCUMENT_IMPORT docImport
LEFT JOIN M_DOCUMENT_CATEGORY docCate
    ON (docCate.CODE = docImport.CODE)
LEFT JOIN M_DOCUMENT_CATEGORY_LANGUAGE docCateLang
    ON (docCateLang.M_CATEGORY_ID = docCate.ID
    AND docCateLang.M_LANGUAGE_CODE = UPPER('VI'))
WHERE
    1 = 1
    AND docImport.SESSION_KEY = /*sessionKey*/'20211314021325246'
 ORDER BY docImport.IS_ERROR DESC, docImport.ID ASC
