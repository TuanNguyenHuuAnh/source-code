WITH role_item AS (
	SELECT role_id
	FROM JCA_ITEM item
	JOIN jca_authority authority
		ON authority.item_id = item.id
		AND authority.deleted_date IS NULL
	WHERE item.deleted_date IS NULL 
		AND item.function_code = /*itemCode*/
)
, role_for_account AS (
	SELECT role_account.account_id
	FROM JCA_ROLE_FOR_ACCOUNT role_account
	JOIN role_item
		ON role_item.role_id = role_account.role_id
	WHERE role_account.deleted_date IS NULL
)
, role_for_team AS (
	SELECT account_team.account_id
	FROM JCA_ROLE_for_team role_team
	JOIN role_item
		ON role_item.role_id = role_team.role_id
	JOIN JCA_ACCOUNT_TEAM account_team
		ON account_team.team_id = role_team.team_id
		AND account_team.deleted_date IS NULL
	WHERE role_team.deleted_date IS NULL
)

SELECT account.*
FROM jca_account account
WHERE 
	    account.deleted_date is null 
	AND account.enabled = 1
	AND (EXISTS (
			SELECT 1
			FROM role_for_account
			WHERE role_for_account.account_id = account.id
			)
		OR EXISTS (
			SELECT 1
			FROM role_for_team
			WHERE role_for_team.account_id = account.id
			)
		)