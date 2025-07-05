SELECT
	"m_item"."id",
	"m_item"."function_code",
	"m_item"."function_name",
	"m_item"."description",
	"m_item"."function_type",
	"m_item"."business_code",
	"m_item"."sub_type",
	"m_item"."CREATED_BY",
	"m_item"."UPDATED_BY",
	"m_item"."UPDATED_DATE",
	"m_item"."DELETED_BY",
	"m_item"."deleted_date",
	"m_item"."CREATED_DATE"
FROM
	HSSA."JCA_ITEM" "m_item"
WHERE
	"m_item"."sub_type"  = /*subType*/