SELECT DISTINCT
	m."id" AS menu_id,
	m."code" AS menu_code,
	ml."name" AS menu_name,
	m."url",
	m."parent_id",
	m."status",
	ml."alias",
	m."sort",
	m."CREATED_DATE",
	m."menu_type",
	m."icon",
	m."check_open"
FROM
	"HSSA"."JCA_MENU_PATH" m
LEFT JOIN "HSSA"."JCA_LANGUAGE" lang ON lang."code" = /*languageCode*/
LEFT JOIN "HSSA"."JCA_MENU_language" ml ON m."id" = ml."m_menu_id" AND ml."m_language_id" = lang."id"
LEFT JOIN "HSSA"."JCA_ITEM" item ON (item."id" = m."item_id")
WHERE
	m."DELETED_BY" IS NULL
	AND m."menu_type" = /*menuType*/
ORDER BY
	m."parent_id",
	m."sort",
	m."CREATED_DATE" DESC