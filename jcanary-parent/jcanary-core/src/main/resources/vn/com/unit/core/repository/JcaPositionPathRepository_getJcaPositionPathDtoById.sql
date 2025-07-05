SELECT
	pos_path.ID					as ID
	,pos_path.DEPTH				as DEPTH
	,pos_path.ANCESTOR_ID		as ANCESTOR_ID
	,pos_path.DESCENDANT_ID		as DESCENDANT_ID
	,pos_path.CREATED_DATE		as CREATED_DATE
	,pos_path.CREATED_ID		as CREATED_ID

FROM 
	JCA_POSITION_PATH pos_path
WHERE  

	 pos_path.ID = /*id*/
