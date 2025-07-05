SELECT
    oi.org_id AS org_id,
    oi.parent_org_id AS parent_org_id,
    oi.org_code AS org_code,
    oi.org_name AS org_name
FROM
    jca_m_org as oi
WHERE 
    1=1 
    AND deleted_date IS NULL
    /*IF delFlg != null && delFlg != ''*/
    AND oi.del_flg = /*delFlg*/
    /*END*/
    /*IF condition.seachByName != null && condition.seachByName != ''*/
    AND oi.org_name LIKE '%'+ /*condition.seachByName*/ +'%' 
    /*END*/

