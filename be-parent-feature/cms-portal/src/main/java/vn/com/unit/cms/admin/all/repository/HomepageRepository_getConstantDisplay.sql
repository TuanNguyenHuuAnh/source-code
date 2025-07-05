SELECT DISTINCT
    cd.type
    , cd.kind
    , cd.cat
    , cd.code
    , cd.cat_official_name
    , cd.cat_abbr_name
FROM
    m_homepage_setting hm
LEFT JOIN jca_constant_display cd ON (cd.cat = hm.banner_page AND hm.delete_date is null) 
WHERE
    hm.delete_date IS NULL
    AND cd.type = /*type*/
ORDER BY cd.cat_official_name ASC, cd.cat_abbr_name ASC