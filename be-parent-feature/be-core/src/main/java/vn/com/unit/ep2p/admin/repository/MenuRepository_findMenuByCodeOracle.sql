SELECT
	*
FROM
	"HSSA"."JCA_MENU_PATH" menu
WHERE
	menu."DELETED_BY" IS NULL
	
    AND menu."code" = /*code*/
    