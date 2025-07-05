SELECT
    CODE AS id,
    NAME AS name,
    NAME AS text
FROM
    JPR_BUSINESSDEFINITION
WHERE 1 = 1
/*IF keySearch != null && keySearch != ''*/
	AND NAME like concat('%',  /*keySearch*/'', '%')
/*END*/
/*IF companyId != null*/
	AND (COMPANY_ID is null or COMPANY_ID = /*companyId*/1)
/*END*/
ORDER BY NAME, CODE
/*IF isPaging == true*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/