WITH countProductCategorySub AS(
        SELECT
            count(*)            AS  countProductCategorySub
        FROM
            m_product_category cate
        LEFT JOIN m_product_category_sub cateSub
            ON(cateSub.delete_date IS NULL
                AND cateSub.m_customer_type_id = cate.m_customer_type_id
                AND cateSub.m_product_category_id = cate.id)
        WHERE
            cate.delete_date IS NULL
            AND cate.id = /*categoryId*/
            AND cateSub.status NOT IN /*lstStatus*/())
    , countProduct AS(
        SELECT
            count(*)            AS  countProduct
        FROM
            m_product_category cate
        LEFT JOIN m_product pro
            ON(pro.delete_date IS NULL
                AND pro.m_customer_type_id = cate.m_customer_type_id
                AND pro.m_product_category_id = cate.id)
        WHERE
            cate.delete_date IS NULL
            AND cate.id = /*categoryId*/
            AND pro.status NOT IN /*lstStatus*/())
    , countFaqs AS(
        SELECT
            count(*)            AS  countFaqs
        FROM
            m_product_category cate
        LEFT JOIN m_faqs faqs
            ON(faqs.delete_date IS NULL
                --AND CHARINDEX(faqs.M_PRODUCT_CATEGORY_ID, cate.id, 1) > 0)
                AND CHARINDEX(CONCAT(',', cate.id, ','), CONCAT(',',faqs.M_PRODUCT_CATEGORY_ID, ',')) > 0)
        WHERE
            cate.delete_date IS NULL
            AND cate.id = /*categoryId*/
            AND faqs.status NOT IN /*lstStatus*/())
    , countNews AS(
        SELECT
            count(*)            AS  countNews
        FROM
            m_product_category cate
        LEFT JOIN m_news news
            ON(news.delete_date IS NULL
                --AND CHARINDEX(news.M_PRODUCT_CATEGORY_ID, cate.id, 1) > 0)
                AND CHARINDEX(CONCAT(',', cate.id, ','), CONCAT(',',news.M_PRODUCT_CATEGORY_ID, ',')) > 0)
        WHERE
            cate.delete_date IS NULL
            AND cate.id = /*categoryId*/
            AND news.status NOT IN /*lstStatus*/())
    , sumAll AS(
        SELECT countProductCategorySub FROM countProductCategorySub
        UNION ALL
        SELECT countProduct FROM countProduct
        UNION ALL
        SELECT countFaqs FROM countFaqs
        UNION ALL
        SELECT countNews FROM countNews
    )
SELECT SUM(countProductCategorySub) FROM sumAll;