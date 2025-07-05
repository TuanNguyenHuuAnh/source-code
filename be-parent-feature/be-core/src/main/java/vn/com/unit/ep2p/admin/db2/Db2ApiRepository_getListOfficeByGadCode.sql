select distinct O.ORG_CODE  AS ID
, O.NAME
, O.ORG_CODE  AS kind
, O.ORG_CODE || ' - ' || O.NAME AS TITLE
      ,ga.GAD_CODE
      ,ga.GAD_EFFECTIVE_DATE
      ,ga.SEGMENT_GA
      ,ga.SEG_EFFECTIVE_DATE
      ,ga.GA_DFA_FLAG
      ,ga.GA_DFA_EFFECTIVE_DATE
      ,ga.REPORT_TO_GA
      ,ga.GA_SUB_EFFECTIVE_DATE
      ,ga.GA_MOTHER_CODE
      ,ga.BANK_CODE
      ,ga.BANK_ACCOUNT_NAME
      ,ga.BANK_BRANCH
      ,ga.BANK_ACCOUNT_NUMBER
      ,ga.DATE_OF_GROWTH
      ,ga.EMAIL_CS
      ,ga.EMAIL_SS
      ,ga.COMPANY
      ,ga.TRANSACTION_NO
      ,ga.VERSION_DATA
      ,ga.CREATED_BY
      ,ga.CREATED_DATE
      ,ga.UPDATED_BY
      ,ga.UPDATED_DATE
      ,ga.SEGMENT_GA_NAME
      ,ga.GA_DFA_FLAG_NAME
      ,ga.ORG_ID
      ,ga.GAD_NAME
      ,ga.EFFECTIVE_DATE
      ,ga.EXPIRED_DATE
      ,ga.INACTIVE
      ,ga.STARTUP_FLAG
from stg_dms.DMS_AA_GA_OFFICE ga
INNER JOIN STG_DMS.DMS_ORGANIZATION O
ON(ga.ORG_CODE = O.ORG_CODE)
where ga.GAD_CODE = /*agentCode*/'119670'
--AND NVL(ga.INACTIVE, 0) = 0
/*IF orgCode != null && orgCode != ''*/
AND O.ORG_CODE = /*orgCode*/
/*END*/
/*IF inActive != null && inActive != ''*/
AND O.INACTIVE = /*inActive*/'0'
/*END*/
;