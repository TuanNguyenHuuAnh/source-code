SELECT
  introCate.*,
  introCatelang.LABEL         as    title
FROM M_INTRODUCTION_CATEGORY introCate
LEFT JOIN M_INTRODUCTION_CATEGORY_LANGUAGE introCatelang
ON (introCatelang.M_INTRODUCE_CATEGORY_ID = introCate.ID 
    AND introCatelang.DELETE_DATE is NULL)
WHERE introCate.DELETE_DATE is NULL
  AND introCate.ENABLED = 1
  AND introCatelang.M_LANGUAGE_CODE = UPPER(/*lang*/)
  /*IF id != null && id != ''*/
  AND introCate.ID != /*id*/
  /*END*/
  /*IF parentId == null || parentId == '' || parentId == -1*/
  AND introCate.PARENT_ID is NULL
  /*END*/
  /*IF parentId != null && parentId != '' && parentId != -1*/
  AND introCate.PARENT_ID = /*parentId*/
  /*END*/
ORDER BY introCate.sort