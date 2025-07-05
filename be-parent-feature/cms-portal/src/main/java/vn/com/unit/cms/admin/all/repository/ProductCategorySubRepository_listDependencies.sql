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
            AND cateSub.id = /*categorySubId*/
            AND pro.status NOT IN /*lstStatus*/())
    , countFaqs AS(
        SELECT
            count(*)            AS  countFaqs
        FROM
            m_product_category_sub cateSub
        LEFT JOIN m_faqs faqs
            ON(faqs.delete_date IS NULL
                --AND CHARINDEX(faqs.m_product_category_sub_ID, cateSub.id, 1) > 0)
                AND CHARINDEX(CONCAT(',', cateSub.id, ','), CONCAT(',',faqs.m_product_category_sub_ID, ',')) > 0)
        WHERE
            cateSub.delete_date IS NULL
            AND cateSub.id = /*categorySubId*/
            AND faqs.status NOT IN /*lstStatus*/())
    , countNews AS(
        SELECT
            count(*)            AS  countNews
        FROM
            m_product_category_sub cateSub
        LEFT JOIN m_news news
            ON(news.delete_date IS NULL
                --AND CHARINDEX(news.m_product_category_sub_ID, cateSub.id, 1) > 0)
                AND CHARINDEX(CONCAT(',', cateSub.id, ','), CONCAT(',',news.m_product_category_sub_ID, ',')) > 0)
        WHERE
            cateSub.delete_date IS NULL
            AND cateSub.id = /*categorySubId*/
            AND news.status NOT IN /*lstStatus*/())
    , sumAll AS(
        SELECT 'Danh sách sản phẩm có ' as name, countProduct FROM countProduct
        UNION ALL
        SELECT 'Danh sách câu hỏi có ' as name, countFaqs FROM countFaqs
        UNION ALL
        SELECT 'Danh sách ưu đãi có ' as name, countNews FROM countNews
    )
SELECT * FROM sumAll;