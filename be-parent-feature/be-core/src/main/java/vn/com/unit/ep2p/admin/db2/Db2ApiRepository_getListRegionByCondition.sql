       select distinct b.AREA_CODE as ID
       , b.AREA_NAME as NAME
       , RPT_ODS.DS_FN_ORG_CODE (AREA_CODE ) as kind
       , RPT_ODS.DS_FN_ORG_CODE (AREA_CODE ) || ' - ' ||  b.AREA_NAME title
     from   RPT_ODS.DS_VW_ORG_STRUCTURE_BY_AGENT b
    /*BEGIN*/
    where
    /*IF territory != null && territory != ''*/
         and INSTR(/*territory*/',243083,',(','|| b.TERRITORY_CODE ||',')) > 0
    /*END*/
    /*IF agentCode != null && agentCode != ''*/
    and locate( ';' || b.AGENT_CODE   ||';' ,';' || /*agentCode*/'391719'  ||';')>0
      /*END*/
      /*END*/