            select distinct b.REGION_CODE AS ID
            , b.REGION_NAME AS NAME
            , RPT_ODS.DS_FN_ORG_CODE (REGION_CODE ) as kind
       		, RPT_ODS.DS_FN_ORG_CODE (REGION_CODE ) || ' - ' ||  b.REGION_NAME title
    from   RPT_ODS.DS_VW_ORG_STRUCTURE_BY_AGENT b
    /*BEGIN*/
    where
     /*IF region != null && region != ''*/
         AND INSTR(/*region*/',N,',(','|| b.AREA_CODE ||',')) > 0
    /*END*/
     /*IF agentCode != null && agentCode != ''*/
    and locate( ';' || b.AGENT_CODE   ||';' ,';' || /*agentCode*/''  ||';')>0
      /*END*/
      /*IF territory != null && territory != ''*/
    AND INSTR(/*territory*/',N,',(','|| b.TERRITORY_CODE ||',')) > 0
      /*END*/
      /*END*/;