WITH countFaqs AS(
        SELECT
            count(*)            AS  countFaqs
        FROM
            m_product pro
        LEFT JOIN m_faqs faqs
            ON(faqs.delete_date IS NULL
                --AND CHARINDEX(faqs.M_PRODUCT_ID, pro.id, 1) > 0)
                AND CHARINDEX(CONCAT(',', pro.id, ','), CONCAT(',',faqs.M_PRODUCT_ID, ',')) > 0)
        WHERE
            pro.delete_date IS NULL
            AND pro.id = /*productId*/100341
            AND faqs.status NOT IN /*lstStatus*/(1, 98, 100))
    , countNews AS(
        SELECT
            count(*)            AS  countNews
        FROM
            m_product pro
        LEFT JOIN m_news news
            ON(news.delete_date IS NULL
                --AND CHARINDEX(news.M_PRODUCT_ID, pro.id, 1) > 0)
                AND CHARINDEX(CONCAT(',', pro.id, ','), CONCAT(',',news.M_PRODUCT_ID, ',')) > 0)
        WHERE
            pro.delete_date IS NULL
            AND pro.id = /*productId*/100341
            AND news.status NOT IN /*lstStatus*/(1, 98, 100))
    , sumAll AS(
        SELECT countFaqs FROM countFaqs
        UNION ALL
        SELECT countNews FROM countNews
    )
SELECT SUM(countFaqs) FROM sumAll;