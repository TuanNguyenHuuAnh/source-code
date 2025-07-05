SELECT DISTINCT 
    AREA_MANAGER_CODE                               AS  ID
    , AREA_MANAGER_NAME                             AS  NAME
    , CONCAT(AREA_MANAGER_CODE , ' - ', AREA_MANAGER_NAME  )     AS  TEXT
FROM ERS_BANCAS_STRUCTURE
WHERE 
    REGION_MANAGER_CODE = /*regionManagerCode*/''