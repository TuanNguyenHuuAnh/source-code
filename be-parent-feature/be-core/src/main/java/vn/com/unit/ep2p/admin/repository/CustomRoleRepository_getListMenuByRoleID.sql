SELECT MENU.ID AS MENU_ID,
      MENU.CODE AS MENU_CODE,
      MENU.URL AS URL,
      MENU.COMPANY_ID AS COMPANY_ID,
      (N'/') AS MENU_NAME
FROM jca_menu_path       main
    LEFT JOIN jca_menu            menu 
        ON main.descendant_id = menu.id
WHERE isnull(MENU.DELETED_ID,0) = 0
	  AND main.ancestor_id = 0 
      AND main.depth = 1
      /*IF companyId!=NULL */
      AND MENU.COMPANY_ID = /*companyId*/
      /*END*/
      /*IF companyId==NULL */
      AND MENU.COMPANY_ID IS NULL
      /*END*/
UNION ( SELECT * FROM ( SELECT MENU.ID AS MENU_ID,
                              MENU.CODE AS MENU_CODE,
                              MENU.URL AS URL,
                              MENU.COMPANY_ID AS COMPANY_ID,
                              MLANG.NAME AS MENU_NAME
                              FROM JCA_MENU MENU
                                    INNER JOIN JCA_ITEM ITEM ON ITEM.ID = MENU.ITEM_ID
                                    INNER JOIN VW_AUTHORITY_PIVOT AUTH ON AUTH.ITEM_ID = ITEM.ID AND AUTH.CAN_ACCESS_FLG = 1 AND AUTH.ROLE_ID =/*roleId*/1
                                    LEFT JOIN JCA_MENU_LANG MLANG ON MENU.ID = MLANG.MENU_ID
                                    INNER JOIN JCA_LANGUAGE LANG ON LANG.ID = MLANG.LANG_ID AND UPPER(LANG.CODE)  = UPPER(/*LANGUAGECODE*/'EN')
                              WHERE isnull(MENU.DELETED_ID,0) = 0 AND MENU.URL NOT LIKE N'/'
                              /*IF null!=companyId */
                              AND MENU.COMPANY_ID = /*companyId*/
                              /*END*/
                              /*IF null==companyId */
                              AND MENU.COMPANY_ID IS NULL
                              /*END*/) A);