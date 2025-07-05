SELECT
    intro.id, 
    intro.parent_id, 
    intro.create_date,
    introCatelang.LABEL         as    title
FROM
     m_introduction_category intro
LEFT JOIN M_INTRODUCTION_CATEGORY_LANGUAGE introCatelang
ON (introCatelang.M_INTRODUCE_CATEGORY_ID = intro.ID 
    AND introCatelang.DELETE_DATE is NULL)
WHERE intro.delete_by is NULL
AND intro.parent_id in /*parentIds*/()
AND introCatelang.M_LANGUAGE_CODE = UPPER(/*lang*/)
ORDER BY
    intro.create_date DESC, intro.code ASC
