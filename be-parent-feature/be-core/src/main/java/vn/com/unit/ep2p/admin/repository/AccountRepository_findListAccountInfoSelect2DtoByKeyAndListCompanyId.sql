WITH wa_acc AS (
    SELECT 
        acc.ID                                                                  AS ACCOUNT_ID
        , acc_org.ORG_ID
        , CASE WHEN acc_org.ORG_ID = /*orgId*/&&org_id THEN 1 ELSE 2 END        AS SORT_ORDER
        , row_number() OVER (PARTITION BY acc.ID ORDER BY
			CASE WHEN acc_org.ORG_ID = /*orgId*/&&org_id THEN 1 ELSE 2 END ASC) RN
    FROM
    	jca_account acc
	LEFT JOIN 
		JCA_M_ACCOUNT_ORG acc_org 
	ON	acc.ID = acc_org.ACCOUNT_ID
		AND acc_org.DELETED_ID = 0
		AND acc_org.ASSIGN_TYPE = 1
    WHERE
        acc.DELETED_ID = 0
        AND acc.ID <> /*accountId*/&&account_id
        AND acc.COMPANY_ID IN /*listCompanyId*/(&&company_id)
),
wa_disp_email AS (
    SELECT
        acc_org.ACCOUNT_ID
        , acc_org.ORG_ID
        , 3                       AS SORT_ORDER
        , row_number() OVER (PARTITION BY acc_org.ACCOUNT_ID ORDER BY acc_org.ACCOUNT_ID DESC) RN
    FROM
        VW_GET_USER_ROLE vw_user_role
    INNER JOIN
        JCA_ROLE_for_display_email display_email
    ON
        vw_user_role.ROLE_ID = display_email.ROLE_ID
        AND display_email.DELETED_ID = 0 
        AND display_email.DEL_FLG = 0
    INNER JOIN
        JCA_M_ACCOUNT_ORG acc_org
    ON
        display_email.ORG_ID = acc_org.ORG_ID
        AND acc_org.DELETED_ID = 0 
    WHERE
        vw_user_role.ACCOUNT_ID = /*accountId*/&&account_id
        AND acc_org.ACCOUNT_ID <> /*accountId*/&&account_id
        AND acc_org.COMPANY_ID NOT IN /*listCompanyId*/(&&company_id)
)
, wa_acct_pos AS (
    SELECT 
        acct_pos.*
        , row_number() OVER (PARTITION BY acct_pos.ACCOUNT_ID, acct_pos.POSITION_ID ORDER BY acct_pos.ID) RN
    FROM JCA_M_ACCOUNT_ORG acct_pos
    WHERE 
        acct_pos.DELETED_ID = 0
        AND acct_pos.ASSIGN_TYPE = 1
)
, account_pos AS (
    SELECT 
        acct_pos.POSITION_NAME_MERGE 
        , acct_pos.ACCOUNT_ID 
        , acct.*
        , row_number() OVER (PARTITION BY acct_pos.ACCOUNT_ID ORDER BY 1) RN
    FROM (
        SELECT 
            acct_pos.ACCOUNT_ID,
            LISTAGG(pos.NAME, ', ') WITHIN GROUP(ORDER BY acct_pos.ID) POSITION_NAME_MERGE
        FROM wa_acct_pos acct_pos
        LEFT JOIN JCA_M_POSITION pos ON pos.ID = acct_pos.POSITION_ID AND pos.DELETED_ID = 0
        WHERE 
            acct_pos.RN = 1
        GROUP BY
            acct_pos.ACCOUNT_ID
    ) acct_pos
    LEFT JOIN jca_account acct ON acct.DELETED_ID = 0 AND acct_pos.ACCOUNT_ID = acct.ID
)

,HO_acc  AS (
    SELECT 
        acc.ID                                                                  AS ACCOUNT_ID
        , acc_org.ORG_ID
        , 4        																AS SORT_ORDER
        , 1																		AS RN
    FROM
    	jca_account acc
   	LEFT JOIN 
		JCA_M_ACCOUNT_ORG acc_org 
	ON	acc.ID = acc_org.ACCOUNT_ID
		AND acc_org.DELETED_ID = 0
		AND acc_org.ASSIGN_TYPE = 1
    WHERE
        acc.DELETED_ID = 0
        AND acc.IS_H_O = 1
        AND acc.ID <> /*accountId*/&&account_id
        AND 1 = (select 1 from jca_account acc where acc.ID = /*accountId*/&&account_id and acc.CAN_SEND_H_O = 1)
)

select 
    data_all.ACCOUNT_ID as ID
    , acc.FULLNAME as NAME
    , CONCAT(acc.FULLNAME, CONCAT(' (', CONCAT(acc.EMAIL,')')))						AS TEXT
    , account_pos.POSITION_NAME_MERGE												AS POSITION_NAME
    , data_all.SORT_ORDER
from
(
  select 
    data_all.ACCOUNT_ID,
    data_all.ORG_ID,
    data_all.SORT_ORDER,
    row_number() OVER (PARTITION BY data_all.ACCOUNT_ID ORDER BY data_all.SORT_ORDER) RN 
    from(
      select * from wa_acc where RN = 1
      UNION
      select * from wa_disp_email where RN = 1
      UNION
      select * from HO_acc where RN = 1
    )data_all
) data_all
LEFT JOIN
jca_account acc
ON
    data_all.ACCOUNT_ID = acc.ID
LEFT JOIN
    account_pos
ON
    data_all.ACCOUNT_ID = account_pos.ACCOUNT_ID
    AND account_pos.RN = 1
WHERE
    acc.DELETED_ID = 0
    AND data_all.RN = 1
    AND acc.ENABLED = 1
    AND (
		UPPER(acc.EMAIL) LIKE CONCAT('%',CONCAT(/*key*/'&&key_search','%'))
		OR FN_CONVERT_TO_VN_ONLY_UPPER(UPPER(acc.FULLNAME)) LIKE CONCAT('%',CONCAT(FN_CONVERT_TO_VN_ONLY_UPPER(/*key*/'&&key_search'),'%'))
	)
ORDER BY data_all.SORT_ORDER, acc.USERNAME
/*IF isPaging*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/