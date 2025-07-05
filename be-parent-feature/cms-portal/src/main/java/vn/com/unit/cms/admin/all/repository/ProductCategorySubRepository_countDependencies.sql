WITH countProduct AS(
        SELECT
            count(*)            AS  countProduct
        FROM
            m_product_category_sub cateSub
        LEFT JOIN m_product pro
            ON(pro.delete_date IS NULL
                AND pro.m_customer_type_id = cateSub.m_customer_type_id
                AND pro.m_product_category_sub_id = cateSub.id)
        WHERE
            cateSub.delete_date IS NULL
            AND cateSub.id = /*categorySubId*/100361
            AND pro.status NOT IN /*lstStatus*/(1, 98, 100))
    , countFaqs AS(
        SELECT
            count(*)            AS  countFaqs
        FROM
            m_product_category_sub cateSub
        LEFT JOIN m_faqs faqs
            ON(faqs.delete_date IS NULL
                AND CHARINDEX(faqs.m_product_category_sub_ID, cateSub.id, 1) > 0)
        WHERE
            cateSub.delete_date IS NULL
            AND cateSub.id = /*categorySubId*/100361
            AND faqs.status NOT IN /*lstStatus*/(1, 98, 100))
    , countNews AS(
        SELECT
            count(*)            AS  countNews
        FROM
            m_product_category_sub cateSub
        LEFT JOIN m_news news
            ON(news.delete_date IS NULL
                AND CHARINDEX(news.m_product_category_sub_ID, cateSub.id, 1) > 0)
        WHERE
            cateSub.delete_date IS NULL
            AND cateSub.id = /*categorySubId*/100361
            AND news.status NOT IN /*lstStatus*/(1, 98, 100))
    , sumAll AS(
        SELECT countProduct FROM countProduct
        UNION ALL
        SELECT countFaqs FROM countFaqs
        UNION ALL
        SELECT countNews FROM countNews
    )
SELECT SUM(countProduct) FROM sumAll;