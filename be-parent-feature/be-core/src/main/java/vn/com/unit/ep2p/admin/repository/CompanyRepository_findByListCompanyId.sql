SELECT
    ID AS id,
    NAME AS name,
    NAME AS text
FROM
    jca_company
WHERE DELETED_ID = 0
	/*IF companyAdmin == false*/
    AND ID IN /*companyIds*/()
	/*END*/
ORDER BY name