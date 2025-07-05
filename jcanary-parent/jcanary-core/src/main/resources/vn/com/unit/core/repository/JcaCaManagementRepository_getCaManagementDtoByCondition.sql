SELECT
     ca.ID               AS CA_MANAGEMENT_ID
	,ca.ACCOUNT_ID       AS ACCOUNT_ID
	,ca.STORE_TYPE       AS STORE_TYPE
	,ca.CA_SLOT          AS CA_SLOT
	,ca.CA_PASSWORD      AS CA_PASSWORD
	,ca.CA_LABEL         AS CA_LABEL
	,ca.CA_SERIAL        AS CA_SERIAL
	,ca.CA_NAME          AS CA_NAME
	,ca.CA_DEFAULT       AS CA_DEFAULT
	,ca.COMPANY_ID       AS COMPANY_ID
FROM 
	JCA_ACCOUNT_CA ca
LEFT JOIN
	JCA_ACCOUNT acc
ON
	ca.ACCOUNT_ID = acc.ID	
	
WHERE 
	ca.DELETED_ID = 0
	/*IF searchDto.companyId != null && searchDto.companyId != 0*/
	AND ca.COMPANY_ID = /*searchDto.companyId*/
	/*END*/
	/*IF searchDto.caName != null && searchDto.caName != ''*/
    OR UPPER(ca.CA_NAME) LIKE concat(concat('%',  UPPER(/*searchDto.caName*/)), '%')
    /*END*/
    /*IF searchDto.accountName != null && searchDto.accountName != ''*/
    OR UPPER(acc.USERNAME) LIKE concat(concat('%',  UPPER(/*searchDto.accountName*/)), '%')
    /*END*/
    /*IF searchDto.caSlot != null && searchDto.caSlot != ''*/
    OR UPPER(ca.CA_LOT) LIKE concat(concat('%',  UPPER(/*searchDto.caSlot*/)), '%')
  /*END*/
/*IF orders != null*/
ORDER BY /*$orders*/ca.CA_NAME
-- ELSE ORDER BY ca.ID DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/