SELECT T.CODE AS CODE, LANG.LABEL AS LABEL, T.LINK_ALIAS AS LINK_ALIAS
FROM M_NEWS_TYPE T
INNER JOIN M_NEWS_TYPE_LANGUAGE LANG
ON(T.ID = LANG.M_NEWS_TYPE_ID)
WHERE T.DELETE_DATE IS NULL
AND LANG.M_LANGUAGE_CODE = UPPER(/*language*/'vi')
/*IF modeView == 0*/
AND T.ENABLED = 1
/*END*/
/*IF channel == null || channel == ''*/
AND isnull(T.CHANNEL, 'AG') = /*channel*/
/*END*/
/*IF channel != null && channel == 'AG'*/
AND isnull(T.CHANNEL, 'AG') = /*channel*/
/*END*/
/*IF channel != null && channel == 'AD'*/
AND T.CHANNEL = /*channel*/
/*END*/
AND T.CODE NOT IN ('SECURITY', 'USERGUIDE')
ORDER BY SORT ASC