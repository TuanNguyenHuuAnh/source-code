    select distinct b.TERRITORY_CODE AS ID
    , b.TERRITORY_NAME as name
    , RPT_ODS.DS_FN_ORG_CODE (TERRITORY_CODE ) as kind
   	, RPT_ODS.DS_FN_ORG_CODE (TERRITORY_CODE ) || ' - ' ||  b.TERRITORY_NAME title
    from   RPT_ODS.DS_VW_ORG_STRUCTURE_BY_AGENT b
    /*BEGIN*/
    where
    /*IF agentCode != null && agentCode != ''*/
    and locate( ';' || b.AGENT_CODE   ||';' ,';' || /*agentCode*/''  ||';')>0
      /*END*/
      /*END*/