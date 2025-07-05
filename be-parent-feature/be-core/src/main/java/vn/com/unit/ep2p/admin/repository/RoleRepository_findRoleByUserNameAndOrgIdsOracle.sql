SELECT
    vw_user_author."function_name"      AS role_name
    , vw_user_author."function_code"    AS item_name
    , vw_user_author."function_type"    AS function_type
    , vw_user_author."access_flg"       AS access_flg
    , vw_user_author."status_code"      AS status
    , vw_user_author."process_id"       AS process_id
FROM
    "HSSA"."vw_get_user_authority" vw_user_author
WHERE
   vw_user_author."username" = /*username*/
   AND vw_user_author."del_flg" = '0'
   AND vw_user_author."org_id" IN /*orgIdList*/()
