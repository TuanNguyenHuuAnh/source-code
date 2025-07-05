select c.ID as id,
        c.NAME as name
from jca_company c
left join JCA_ROLE_FOR_COMPANY rc on c.ID = rc.COMPANY_ID
left join JCA_ROLE_FOR_TEAM rt on rt.ROLE_ID = rc.ROLE_ID
left join JCA_ACCOUNT_TEAM at on at.TEAM_ID = rt.TEAM_ID
left join JCA_ACCOUNT a on a.ID = at.ACCOUNT_ID
where
    /*IF user != null*/
    a.USERNAME = /*user*/''
    /*END*/