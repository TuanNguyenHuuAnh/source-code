select count(*) 
from (select
        at.ACCOUNT_ID, r.ROLE_TYPE 
        from JCA_ACCOUNT_TEAM at
        join JCA_ROLE_FOR_TEAM rt on rt.TEAM_ID = at.TEAM_ID and at.DELETED_ID = 0 and rt.DELETED_ID = 0
        join JCA_ROLE r on rt.ROLE_ID = r.ID and r.DELETED_ID = 0
        where at.ACCOUNT_ID  = /*accountId*/1
        group by at.ACCOUNT_ID, r.ROLE_TYPE
        UNION 
        select 
            /*accountId*/1 as ACCOUNT_ID, r.ROLE_TYPE 
        from JCA_ROLE_FOR_TEAM rt
        left join JCA_ROLE r on rt.ROLE_ID = r.ID and r.DELETED_ID = 0 and rt.DELETED_ID = 0
        where
        /*IF teamId != null && teamId > 0*/
        	rt.TEAM_ID = /*teamId*/1
        /*END*/
        /*IF teamId == null*/
       		1 = 0
        /*END*/
        group by r.ROLE_TYPE) RT