SELECT
    ID                      AS ID
    ,CODE                   AS CODE
    ,NAME                   AS NAME
    ,PACKAGE_IMAGE_URL      AS PACKAGE_IMAGE_URL
    ,IMAGE_URL              AS IMAGE_URL
    ,IMAGE_REPO_ID          AS IMAGE_REPO_ID
    ,DISPLAY_ORDER          AS DISPLAY_ORDER
    ,CREATED_DATE           AS CREATED_DATE
    ,CREATED_ID             AS CREATED_ID
    ,UPDATED_DATE           AS UPDATED_DATE
    ,UPDATED_ID             AS UPDATED_ID
    ,DELETED_ID             AS DELETED_ID
    ,DELETED_DATE           AS DELETED_DATE
    ,VERSION                AS VERSION 
FROM
    jca_language
WHERE DELETED_ID = 0