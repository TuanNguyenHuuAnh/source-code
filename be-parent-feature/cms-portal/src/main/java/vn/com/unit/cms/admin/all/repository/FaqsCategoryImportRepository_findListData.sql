SELECT 
     faqsImport.id                    as id
    ,faqsImport.CODE                  as code
    ,faqsImport.TITLE                 as title
    ,faqsImport.KEYWORDS_DESC         as keywords_desc
    ,faqsImport.CONTENT               as content
    ,faqsImport.ENABLED               as enabled
    ,faqsImport.POSTED_DATE           as posted_date
    ,faqsImport.EXPIRATION_DATE       as expiration_date
    ,faqsImport.MESSAGE_ERROR         as message_error
    ,faqsImport.IS_ERROR              as is_error
    ,faqsImport.FAQS_CODE             as faqs_code
    ,faqsCateLang.TITLE               as faqs_Cate_Title
    ,faqsImport.KEYWORDS_SEO        as keywords_seo
    ,faqsImport.KEYWORDS            as keywords
FROM M_FAQS_CATEGORY_IMPORT faqsImport
LEFT JOIN M_FAQS_CATEGORY faqsCate
    ON (faqsCate.CODE = faqsImport.CODE)
LEFT JOIN M_FAQS_CATEGORY_LANGUAGE faqsCateLang
    ON (faqsCateLang.M_FAQS_CATEGORY_ID = faqsCate.ID
    AND faqsCateLang.M_LANGUAGE_CODE = UPPER('VI'))
WHERE
    1 = 1
    AND faqsImport.SESSION_KEY = /*sessionKey*/'20211314021325246'
 ORDER BY faqsImport.IS_ERROR DESC, faqsImport.ID ASC
OFFSET /*offset*/0 ROWS FETCH NEXT /*sizeOfPage*/20 ROWS ONLY