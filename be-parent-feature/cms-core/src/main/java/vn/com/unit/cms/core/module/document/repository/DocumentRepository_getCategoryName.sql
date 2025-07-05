SELECT category.title	AS category_name
FROM M_DOCUMENT tbl
    INNER JOIN M_DOCUMENT_language tblLang WITH (NOLOCK)
    ON tbl.id = tblLang.M_DOCUMENT_id
    LEFT JOIN M_DOCUMENT_CATEGORY_LANGUAGE category WITH (NOLOCK)
    ON category.M_CATEGORY_ID = tbl.M_CATEGORY_ID
    AND category.m_language_code = tblLang.m_language_code
    AND category.delete_date is null
    WHERE	tbl.id = /*id*/'5'
    AND tbl.delete_date is null
    AND UPPER(tblLang.m_language_code) = UPPER(/*lang*/'VI')