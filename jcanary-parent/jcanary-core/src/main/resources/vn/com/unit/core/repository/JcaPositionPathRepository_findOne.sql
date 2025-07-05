Select * 
From
	JCA_POSITION_PATH pos_path
WHERE 
	pos_path.DESCENDANT_ID = /*descendantId*/
	AND
	pos_path.ANCESTOR_ID = /*ancestorId*/

