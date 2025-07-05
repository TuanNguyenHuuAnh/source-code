SELECT
    intro.*
FROM m_introduction intro
LEFT JOIN m_introduction_category_language catelang
ON (catelang.delete_date is null
    AND UPPER(catelang.m_language_code) = UPPER(/*language*/'vi')
    AND intro.m_introduction_category_id = catelang.m_introduce_category_id
)
WHERE
    intro.delete_by is NULL
ORDER BY
    catelang.LABEL, intro.sort ASC, intro.create_date DESC
OFFSET /*offset*/0 ROWS FETCH NEXT /*sizeOfPage*/10 ROWS ONLY;