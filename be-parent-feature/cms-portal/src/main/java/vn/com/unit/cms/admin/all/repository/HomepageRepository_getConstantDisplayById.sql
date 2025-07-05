SELECT
    cd.type
    , cd.kind
    , cd.cat
    , cd.code
    , cd.cat_official_name
    , cd.cat_abbr_name
FROM
    jca_constant_display cd
LEFT JOIN m_homepage_setting hm ON (cd.cat = hm.banner_page AND hm.delete_date is null) 
WHERE
    cd.deleted_by IS NULL
    AND cd.type = /*type*/
    AND hm.id = /*homePageId*/
ORDER BY cd.cat_official_name ASC, cd.cat_abbr_name ASC