select acc.ID as id
	   , acc.USERNAME as name
	   , CONCAT(CONCAT(acc.FULLNAME, CONCAT(' ( ', acc.EMAIL)), ' )') as text
from jca_account acc
where 
	DELETED_ID = 0 
	AND ENABLED = 1 
	/*IF accountIds != null*/
	AND acc.ID IN /*accountIds*/(0)
	/*END*/
	/*IF key != null*/
	AND (UPPER(acc.EMAIL) LIKE CONCAT(CONCAT('%', UPPER(/*key*/'')), '%') OR UPPER(acc.FULLNAME) LIKE CONCAT(CONCAT('%', UPPER(/*key*/'')), '%'))	
	/*END*/
ORDER BY 
	acc.USERNAME
	, acc.EMAIL 
/*IF isPaging == 1*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/