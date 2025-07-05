SELECT
	 item."function_name" AS function_name
	,item."function_code" AS function_code
	,item."function_type" AS function_type
	,authority."access_flg" AS access_flg
	,ps."status_code" AS status_code
	,ps."process_id" AS process_id
FROM
	"HSSA"."jca_authority" authority
INNER JOIN "HSSA"."JCA_ROLE" role ON (authority."role_id" = role."id")
INNER JOIN "HSSA"."JCA_ITEM" item ON (authority."item_id" = item."id")
INNER JOIN "HSSA"."JCA_ROLE_for_org" rfo ON (role."id" = rfo."role_id")
INNER JOIN "HSSA"."jca_m_account" account ON (rfo."org_id" = account."branch_id")
LEFT JOIN "HSSA"."jca_m_status_authority" sa ON (
	item."function_type" = '1' AND item."business_code" IS NOT NULL 
	AND role."id" = sa."role_id" AND sa."item_id" = item."id"
)
LEFT JOIN "HSSA"."jca_t_process_status" ps ON (
	item."function_type" = '1' AND item."business_code" IS NOT NULL
	AND ps."bussines_code" = item."business_code"
	AND ps."id" = sa."process_status_id"
)
WHERE
	account."id" = /*accountId*/