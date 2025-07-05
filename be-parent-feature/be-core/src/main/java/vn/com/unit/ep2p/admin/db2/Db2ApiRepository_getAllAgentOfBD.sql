with Org_Active as 
(SELECT T1.*
FROM  RPT_ODS.D_CURRENT_ORG_HIERARCHY T1
INNER JOIN STG_DMS.DMS_ORGANIZATION T2 on T1.O_CODE = T2.ORG_CODE 
WHERE  T2.INACTIVE = '0'
),
Agent_BD as (
SELECT AGENT_CODE, ROLENAME
FROM (SELECT AGENT_CODE, ROLENAME,ROW_NUMBER () OVER (PARTITION BY AGENT_CODE ORDER BY IDX)AS ORDERBY
      FROM (  SELECT DISTINCT BDTH_CODE AS AGENT_CODE, 1 AS IDX, 'BDTH' AS ROLENAME FROM Org_Active
              UNION ALL
              SELECT DISTINCT BDAH_CODE, 2, 'BDAH' FROM Org_Active
              UNION ALL
              SELECT DISTINCT BDRH_CODE, 3, 'BDRH' FROM Org_Active
              UNION ALL
              SELECT DISTINCT BDOH_CODE, 4, 'BDOH' FROM Org_Active)) 
WHERE ORDERBY = 1 
  AND AGENT_CODE = /*agentCode*/'263632' 
)

SELECT A.AGENT_TYPE
	, A.AGENT_CODE
	, A.AGENT_NAME
	, B.ID_NUMBER
	, (case B.GENDER when 'M' then 'Nam'
    	when 'F' then 'Nữ'
    	else null end) GENDER
	, B.MOBILE_PHONE
	, B.EMAIL_ADDRESS1
	, NVL(C.FULL_ADDRESS, E.FULL_ADDRESS) as FULL_ADDRESS
	, D.TD_CODE TERRITORY
	, D.N_CODE AREA
	, D.R_CODE REGION
	, D.O_CODE OFFICE
	, A.AGENT_TYPE POSITION
	, A.SALES_OFFICE_CODE
	, A.SALES_OFFICE_NAME
FROM STG_DMS.DMS_AGENT_DETAIL A
LEFT JOIN STG_DMS.DMS_CLIENT_DETAIL B
	ON A.CLIENT_CODE = B.CLIENT_CODE
LEFT JOIN STG_DMS.DMS_CLIENT_ADDRESS C
	ON (A.CLIENT_CODE = C.CLIENT_CODE
	and C.ADDRESS_TYPE ='Pref')
LEFT JOIN RPT_ODS.D_CURRENT_ORG_HIERARCHY D
	ON (A.OFFICE_CODE = D.O_CODE)
LEFT JOIN STG_DMS.DMS_CLIENT_ADDRESS E
	ON (A.CLIENT_CODE = E.CLIENT_CODE
	and E.ADDRESS_TYPE ='Res')
WHERE 1=1
and A.AGENT_CODE IN (
	select T2.AGENT_KEY As participants
	from Agent_BD T1 
	inner join RPT_ODS.D_CURRENT_AGENT_HIERARCHY  T2 on T1.AGENT_CODE = t2.BDTH_CODE 
	where T2.LV3_STATUS ='Inforce' AND T1.ROLENAME ='BDTH'
	union
	select distinct T2.CAO_CODE As participants
	from Agent_BD T1 
	inner join Org_Active  T2 on T1.AGENT_CODE = t2.BDTH_CODE 
	where T1.ROLENAME ='BDTH' 
	union
	select distinct T2.BDAH_CODE As participants
	from Agent_BD T1 
	inner join Org_Active  T2 on T1.AGENT_CODE = t2.BDTH_CODE 
	where T1.ROLENAME ='BDTH' 
	union
	select distinct T2.BDRH_CODE As participants
	from Agent_BD T1 
	inner join Org_Active  T2 on T1.AGENT_CODE = t2.BDTH_CODE 
	where T1.ROLENAME ='BDTH' 
	union
	select distinct T2.BDOH_CODE As participants
	from Agent_BD T1 
	inner join Org_Active  T2 on T1.AGENT_CODE = t2.BDTH_CODE 
	where T1.ROLENAME ='BDTH' 
	union
	select T2.AGENT_KEY As participants
	from Agent_BD T1 
	inner join RPT_ODS.D_CURRENT_AGENT_HIERARCHY  T2 on T1.AGENT_CODE = t2.BDAH_CODE 
	where T2.LV3_STATUS ='Inforce' AND T1.ROLENAME ='BDAH'
	union
	select distinct T2.BDTH_CODE As participants
	from Agent_BD T1 
	inner join Org_Active  T2 on T1.AGENT_CODE = t2.BDAH_CODE 
	where T1.ROLENAME ='BDAH'  
	union
	select distinct T2.BDRH_CODE As participants
	from Agent_BD T1 
	inner join Org_Active  T2 on T1.AGENT_CODE = t2.BDAH_CODE 
	where T1.ROLENAME ='BDAH'  
	union
	select distinct T2.BDOH_CODE As participants
	from Agent_BD T1 
	inner join Org_Active  T2 on T1.AGENT_CODE = t2.BDAH_CODE 
	where T1.ROLENAME ='BDAH'  
	union
	select T2.AGENT_KEY As participants
	from Agent_BD T1 
	inner join RPT_ODS.D_CURRENT_AGENT_HIERARCHY  T2 on T1.AGENT_CODE = t2.BDRH_CODE 
	where T2.LV3_STATUS ='Inforce' AND T1.ROLENAME ='BDRH'
	union
	select distinct T2.BDAH_CODE As participants
	from Agent_BD T1 
	inner join Org_Active  T2 on T1.AGENT_CODE = t2.BDRH_CODE 
	where T1.ROLENAME ='BDRH' 
	union
	select distinct T2.BDOH_CODE As participants
	from Agent_BD T1 
	inner join Org_Active  T2 on T1.AGENT_CODE = t2.BDRH_CODE 
	where T1.ROLENAME ='BDRH' 
	union
	select T2.AGENT_KEY As participants
	from Agent_BD T1 
	inner join RPT_ODS.D_CURRENT_AGENT_HIERARCHY  T2 on T1.AGENT_CODE = t2.BDOH_CODE 
	where T2.LV3_STATUS ='Inforce' AND T1.ROLENAME ='BDOH'
	union
	select distinct T2.BDRH_CODE As participants
	from Agent_BD T1 
	inner join Org_Active  T2 on T1.AGENT_CODE = t2.BDOH_CODE 
	where T1.ROLENAME ='BDOH'
)
and A.CHANNEL = 'AG'