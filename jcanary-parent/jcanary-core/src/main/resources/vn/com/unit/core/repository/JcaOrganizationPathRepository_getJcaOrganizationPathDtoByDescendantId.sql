SELECT
	org_path.DEPTH				as DEPTH
	,org_path.ANCESTOR_ID		as ANCESTOR_ID
	,org_path.DESCENDANT_ID		as DESCENDANT_ID
	,org_path.CREATED_DATE		as CREATED_DATE
	,org_path.CREATED_ID		as CREATED_ID

FROM 
	JCA_ORGANIZATION_PATH org_path
WHERE  
	org_path.DESCENDANT_ID = /*descendantId*/
