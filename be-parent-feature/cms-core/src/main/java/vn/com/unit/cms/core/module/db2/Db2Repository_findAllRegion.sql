       select a.ORG_CODE AS ID, a.ORG_CODE||' - '||A.NAME  AS NAME
    from STG_DMS.DMS_ORGANIZATION a
    where a.ORG_TYPE = 'N'
    and a.INACTIVE = 0
    /*IF condition != null && condition.territory != ''*/
        and  INSTR(/*condition.territory*/',N,',(','||UPPER(a.ORG_PARENT_CODE)||',')) > 0
    /*END*/