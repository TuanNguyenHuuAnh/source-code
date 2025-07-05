    select a.ORG_CODE AS ID, a.ORG_CODE||' - '||A.NAME  AS NAME
    from STG_DMS.DMS_ORGANIZATION a
    where a.ORG_TYPE = 'TD'
    and a.INACTIVE = 0