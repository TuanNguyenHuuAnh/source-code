SELECT
    introduction.*,    
    introduction.m_introduction_category_id AS categoryId
    lang.title AS title
FROM
    m_introduction introduction
JOIN
   m_introduction_language lang
   ON (introduction.id = lang.m_introduction_id
      AND lang.m_language_code = UPPER(/*languageCode*/)
      AND lang.delete_by is null)
WHERE introduction.delete_by is NULL
ORDER BY
    introduction.create_date DESC, introduction.m_introduction_category_id ASC, introduction.code ASC
OFFSET /*offset*/0 ROWS FETCH NEXT /*sizeOfPage*/10 ROWS ONLY;