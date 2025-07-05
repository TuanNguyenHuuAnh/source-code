--
-- CaManagementRepository_countCaManagementList.sql
SELECT
	COUNT(*)
FROM JCA_ACCOUNT_CA ITEM
LEFT JOIN JCA_ACCOUNT AC ON ITEM.ACCOUNT_ID = AC.ID AND AC.DELETED_ID = 0
WHERE ITEM.DELETED_ID = 0
		/*IF search.companyId != null && search.companyId != 0*/
	AND ITEM.COMPANY_ID = /*search.companyId*/
	/*END*/
	/*IF search.companyId == 0 && !search.companyAdmin*/
	AND
		(
			1=2
			/*IF search.companyIdList != null && search.companyIdList.size() > 0*/
			OR ITEM.COMPANY_ID  IN /*search.companyIdList*/()
			/*END*/
			OR ITEM.COMPANY_ID IS NULL
		)
	/*END*/
	/*BEGIN*/
		AND 
			(
				/*IF search.caName != null && search.caName != ''*/
			    OR UPPER(ITEM.CA_NAME) LIKE concat(concat('%',  UPPER(/*search.caName*/)), '%')
			    /*END*/
				/*IF search.accountName != null && search.accountName != ''*/
				OR UPPER(AC.USERNAME) LIKE concat(concat('%',  UPPER(/*search.accountName*/)), '%')
				/*END*/
				/*IF search.caSlot != null && search.caSlot != ''*/
				OR UPPER(ITEM.CA_SLOT) LIKE concat(concat('%',  UPPER(/*search.caSlot*/)), '%')
				/*END*/
			)
	/*END*/