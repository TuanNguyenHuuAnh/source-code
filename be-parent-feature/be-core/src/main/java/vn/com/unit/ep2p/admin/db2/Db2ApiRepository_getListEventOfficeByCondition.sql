           select distinct b.OFFICE_CODE AS ID
           , b.OFFICE_NAME AS NAME
           , b.OFFICE AS kind
           , b.OFFICE || ' - ' ||  b.OFFICE_NAME title
    from   RPT_ODS.DS_VW_ORG_STRUCTURE_BY_AGENT b
    /*BEGIN*/
    where
     /*IF area != null && area != ''*/
          AND INSTR(/*area*/',N,',(','|| b.REGION_CODE ||',')) > 0
    /*END*/
     /*IF agentCode != null && agentCode != ''*/
    and locate( ';' || b.AGENT_CODE   ||';' ,';' || /*agentCode*/''  ||';')>0
      /*END*/
      /*IF region != null && region != ''*/
         AND INSTR(/*region*/',N,',(','|| b.AREA_CODE ||',')) > 0
    /*END*/
    /*IF territory != null && territory != ''*/
    AND INSTR(/*territory*/',N,',(','|| b.TERRITORY_CODE ||',')) > 0
      /*END*/
      /*END*/
    ORDER BY b.OFFICE_NAME ASC;