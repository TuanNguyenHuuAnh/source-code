SELECT
		con.CODE                      AS CODE
		,con.CAT_OFFICIAL_NAME		   AS NAME
		
FROM JCA_CONSTANT_DISPLAY con
WHERE 
  	con.TYPE = /*type*/''