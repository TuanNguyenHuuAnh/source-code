SELECT
    vw.org_id AS id,
    vw.org_code AS name,
    vw.org_name_merge AS text
FROM
 VW_GET_ORG_FOR_ACCOUNT vw
WHERE 
	RN = 1
	AND vw.account_id = /*accountId*/1105
