
SELECT cate.*
FROM M_DOCUMENT_CATEGORY cate
INNER JOIN JCA_CONSTANT const
ON(cate.CODE = const.CODE and const.GROUP_CODE = 'LIBRARY_DOCUMENT' AND const.KIND = /*constantCode*/'SCHEDULE_EXAM' and const.LANG_CODE = UPPER(/*lang*/'VI'))