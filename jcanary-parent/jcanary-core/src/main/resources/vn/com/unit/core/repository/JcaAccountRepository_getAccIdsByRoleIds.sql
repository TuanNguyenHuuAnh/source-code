WITH item as (
		SELECT 
			item.ID
			, item.FUNCTION_CODE
			, item.FUNCTION_NAME
			, item.FUNCTION_TYPE 
		FROM JCA_ITEM item
		WHERE 
			item.DELETED_ID = 0
	)
	, authority as (
		SELECT
			authority.ITEM_ID
			, authority.ROLE_ID
			, authority.ACCESS_FLAG
		FROM JCA_AUTHORITY authority
		
	)
	, account as (
		SELECT 
			account.ID							ACCOUNT_ID
		FROM JCA_ACCOUNT account
		WHERE
			account.DELETED_ID = 0
			AND account.ENABLED = '1'
	)
	, account_position as (
		SELECT 
			account.ACCOUNT_ID
			, acct_pos.POSITION_ID
			, ROW_NUMBER() OVER (PARTITION BY acct_pos.POSITION_ID ORDER BY acct_pos.POSITION_ID) RN
		FROM account
		INNER JOIN JCA_ACCOUNT_ORG acct_pos 
			ON (
				acct_pos.ACCOUNT_ID = account.ACCOUNT_ID
--				AND acct_pos.ASSIGN_TYPE = '1'
			)
	)
	-- Role for team
	, account_team_role as (
		SELECT 
			role.ID								ROLE_ID
			, account.ACCOUNT_ID				ACCOUNT_ID
		FROM account 
		INNER JOIN JCA_ACCOUNT_TEAM acctm ON ( account.ACCOUNT_ID = acctm.ACCOUNT_ID)
		INNER JOIN JCA_ROLE_FOR_TEAM roletm ON (acctm.TEAM_ID = roletm.TEAM_ID)
		INNER JOIN JCA_ROLE role ON (role.DELETED_ID = 0 AND role.ACTIVED = '1' AND roletm.role_id = role.id)		
	)
	-- Role for account
	, account_role as (
		SELECT 
			role.ID								ROLE_ID
			, account.ACCOUNT_ID				ACCOUNT_ID
		FROM account 
		INNER JOIN JCA_ROLE_FOR_ACCOUNT rfa 
			ON (
				 rfa.ACCOUNT_ID = account.ACCOUNT_ID
			)
		INNER JOIN JCA_ROLE role ON (role.DELETED_ID = 0 AND role.ACTIVED = '1' AND role.ID = rfa.ROLE_ID)			
	)
	 -- Role for position
	, account_position_role as (
		SELECT 
			role.ID								ROLE_ID
			, account_position.ACCOUNT_ID		ACCOUNT_ID
		FROM account_position 
		INNER JOIN JCA_ROLE_FOR_POSITION rfp ON (rfp.POSITION_ID = account_position.POSITION_ID)
		INNER JOIN JCA_ROLE role ON (role.DELETED_ID = 0 AND role.ACTIVED = '1' AND role.ID = rfp.ROLE_ID)
	)
	
	
	SELECT
		perm.ACCOUNT_ID
	FROM (
		SELECT
			all_role.ROLE_ID
			, authority.ACCESS_FLAG
			, all_role.ACCOUNT_ID
			, ROW_NUMBER() OVER (PARTITION BY all_role.ACCOUNT_ID
       ORDER BY all_role.ROLE_ID) RN
		FROM (
			-- Role for team
			SELECT
				account_team_role.ACCOUNT_ID
				,account_team_role.ROLE_ID
			FROM account_team_role

			UNION

			-- Role for account
			SELECT
				account_role.ACCOUNT_ID
				,account_role.ROLE_ID
			FROM account_role

			UNION

			-- Role for position
			SELECT
				account_position_role.ACCOUNT_ID
				,account_position_role.ROLE_ID
			FROM account_position_role
		) all_role
		INNER JOIN authority ON (authority.ROLE_ID = all_role.ROLE_ID)
		WHERE all_role.ROLE_ID IN /*roleIds*/()
      ) perm where RN = 1;