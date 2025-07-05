SELECT  category.title                                                            AS category_name
FROM m_faqs tbl
         INNER JOIN m_faqs_language tblLang WITH (NOLOCK)
ON tbl.id = tblLang.m_faqs_id

    LEFT JOIN m_faqs_category_language category WITH (NOLOCK)
ON category.m_faqs_category_id = tbl.m_faqs_category_id
    AND category.m_language_code = tblLang.m_language_code
    AND category.delete_date is null
WHERE	tbl.id = /*id*/'5'
  AND tbl.delete_date is null
  AND UPPER(tblLang.m_language_code) = UPPER(/*lang*/'VI')