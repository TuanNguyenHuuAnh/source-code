SELECT
    ID AS id,
    NAME AS name,
    NAME AS text
FROM
    jca_company
WHERE DELETED_ID = 0
/*IF !companyAdmin*/
	AND ID = /*companyId*/1
/*END*/
/*IF keySearch != null && keySearch != ''*/
	AND UPPER(NAME) like concat('%',  concat(UPPER(/*keySearch*/''), '%'))
/*END*/
ORDER BY NAME
/*IF isPaging == true*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/