

with tmp as (
    select a.*
    from STG_DMS.DMS_AGENT_DISCIPLINE a
    where a.AGENT_CODE = /*agentCode*/'152225'
    and a.DISCIPLINE_CODE = '01'
    order by nvl(a.updated_date,a.CREATED_DATE) desc
    FETCH FIRST 1 ROWS ONLY
    )
select Count(*) as COUNT from tmp a
where (a.EXPIRED_DATE is null or a.EXPIRED_DATE >= CURRENT DATE)
