
WITH TH AS (
  SELECT A.ORG_CODE
  FROM STG_DMS.DMS_ORGANIZATION A
  WHERE A.ORG_TYPE = 'TD'
  AND A.INACTIVE = 0
  /*IF condition != null && condition.territory != ''*/
        and  INSTR(/*condition.territory*/',SEAS,SOUTH,',(','||UPPER(a.ORG_CODE)||',')) > 0
    /*END*/
)
, N AS (
  SELECT
      A.ORG_CODE, A.ORG_PARENT_CODE
  FROM STG_DMS.DMS_ORGANIZATION A
  INNER JOIN TH
    ON TH.ORG_CODE =  UPPER(A.ORG_PARENT_CODE)
  WHERE A.ORG_TYPE = 'N'
  AND A.INACTIVE = 0
 /*IF condition != null && condition.region != ''*/
        and  INSTR(/*condition.region*/',SEAS,SOUTH,',(','||UPPER(a.ORG_CODE)||',')) > 0
    /*END*/
)
, R AS (
  SELECT
      A.ORG_CODE
    , A.ORG_PARENT_CODE
  FROM STG_DMS.DMS_ORGANIZATION A
  INNER JOIN N
      ON N.ORG_CODE =  UPPER(A.ORG_PARENT_CODE)
  WHERE A.ORG_TYPE = 'R'
    AND A.INACTIVE = 0
    /*IF condition != null && condition.area != ''*/
        and  INSTR(/*condition.area*/',SEAS,SOUTH,',(','||UPPER(a.ORG_CODE)||',')) > 0
    /*END*/
)
select a.ORG_CODE AS ID, a.ORG_CODE|| ' - '||A.NAME  AS NAME
from STG_DMS.DMS_ORGANIZATION a
    INNER JOIN R
    ON R.ORG_CODE =  UPPER(A.ORG_PARENT_CODE)
    where a.ORG_TYPE = 'O'
    and a.INACTIVE = 0
