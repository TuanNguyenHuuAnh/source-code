SELECT
     COUNT(1)
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
  