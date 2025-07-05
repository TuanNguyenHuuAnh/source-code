SELECT
    vw.position_id AS id,
    vw.position_code AS name,
    vw.position_name_merge AS text
FROM
 VW_GET_POSITION_FOR_ACCOUNT vw
WHERE 
	RN = 1
	AND vw.account_id = /*accountId*/1700
