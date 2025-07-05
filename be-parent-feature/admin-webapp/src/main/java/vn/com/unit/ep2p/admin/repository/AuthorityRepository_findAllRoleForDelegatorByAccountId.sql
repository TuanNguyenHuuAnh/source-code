SELECT
	delegator.FUNCTION_NAME 		
	,delegator.FUNCTION_CODE		
	,delegator.FUNCTION_TYPE		
    ,delegator.ACCESS_FLG	
    ,delegator.STATUS_CODE 		
	,delegator.PROCESS_ID 			
    ,delegator.ACCOUNT_ID 				
    ,delegator.SUB_TYPE	
FROM VW_GET_AUTHORITY_DELEGATE_ACCOUNT delegator 
WHERE 
	delegator.account_Id = /*accountId*/0 
	AND delegator.EFFECTED_DATE <= /*expiredDate*/ 
	AND delegator.EXPIRED_DATE >= /*expiredDate*/sysdate