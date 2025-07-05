WITH countProductCategory AS(
        SELECT count(*)            AS  countProductCategory
        FROM m_product_category cate 
        LEFT JOIN M_Product_category_language catelang
            ON(cate.id = catelang.m_product_category_id
                AND catelang.delete_date is NULL
            )
        LEFT JOIN M_Banner_language bannerLang
            ON(catelang.m_language_code = bannerLang.m_language_code
                AND (catelang.banner_desktop = bannerLang.id
                    OR catelang.banner_mobile = bannerLang.id
                ))
        WHERE
            cate.delete_date is NULL
            AND cate.status NOT IN /*lstStatus*/()
            AND bannerlang.banner_id = /*bannerId*/)
    , countProductCategorySub AS(
        SELECT  count(*)            AS  countProductCategorySub
        FROM m_product_category_sub cateSub
        LEFT JOIN M_Product_category_sub_language cateSublang
            ON(cateSub.id = cateSublang.m_product_category_sub_id
                AND cateSublang.delete_date is NULL
            )
        LEFT JOIN M_Banner_language bannerLang
            ON(cateSublang.m_language_code = bannerLang.m_language_code
                AND (cateSublang.banner_desktop = bannerLang.id
                    OR cateSublang.banner_mobile = bannerLang.id
                ))
        WHERE
            cateSub.delete_date is NULL
            AND cateSub.status NOT IN /*lstStatus*/()
            AND bannerlang.banner_id = /*bannerId*/)
    , countProduct AS(
        SELECT  count(*)            AS  countProduct
        FROM m_product pro
        LEFT JOIN M_Product_language proLang
            ON(pro.id = proLang.m_product_id
                AND proLang.delete_date is NULL
            )
        LEFT JOIN M_Banner_language bannerLang
            ON(proLang.m_language_code = bannerLang.m_language_code
                AND (proLang.banner_desktop = bannerLang.id
                    OR proLang.banner_mobile = bannerLang.id
                ))
        WHERE
            pro.delete_date is NULL
            AND pro.status NOT IN /*lstStatus*/()
            AND bannerlang.banner_id = /*bannerId*/)
    , countHomepage AS(
        SELECT COUNT(*)            AS  countHomepage
        FROM M_HOMEPAGE_SETTING homepageSetting
        WHERE
            homepageSetting.DELETE_DATE IS NULL
            AND homepageSetting.STATUS NOT IN /*lstStatus*/()
            AND (homepageSetting.M_BANNER_TOP_ID LIKE concat('%',/*bannerId*/,'%') 
                OR homepageSetting.M_BANNER_TOP_MOBILE_ID LIKE concat('%',/*bannerId*/,'%')))
    , sumAll AS(
        SELECT 'Danh sách loại sản phẩm có ' as name, countProductCategory FROM countProductCategory
        UNION ALL
        SELECT 'Danh sách danh mục sản phẩm có ' as name, countProductCategorySub FROM countProductCategorySub
        UNION ALL
        SELECT 'Danh sách sản phẩm có ' as name, countProduct FROM countProduct
        UNION ALL
        SELECT 'Danh sách thiết lập banner cho trang có ' as name, countHomepage FROM countHomepage
    )
SELECT * FROM sumAll;