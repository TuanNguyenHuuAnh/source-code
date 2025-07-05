       select a.OFFICE_CODE AS ID, A.NAME AS NAME
    from STG_DMS.DMS_ORGANIZATION a
    where a.ORG_TYPE = 'O'
    and a.INACTIVE = 0
    /*IF province != null && province != ''*/
    and left(a.zip_code, 2) = /*province*/
    /*END*/