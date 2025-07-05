SELECT ROW_NUMBER() OVER(ORDER BY IS_ERROR DESC, IS_WARNING DESC, ID asc) AS NO
	, MESSAGE_ERROR
	, MESSAGE_WARNING
	, TYPE
    , case when TYPE = '1' then AGENT_CODE
		when TYPE IN ('2','3') then ''
		else (case when LEN(AGENT_CODE) <= 7 then AGENT_CODE else '' end) end as AGENT_CODE
	, case when TYPE = '1' then ID_NUMBER
		when TYPE IN ('2','3') then AGENT_CODE
		else (case when LEN(AGENT_CODE) > 7 then AGENT_CODE else '' end) end as ID_NUMBER
    , NAME
    , GENDER
    , EMAIL
    , TEL
    , ADDRESS
    , REFER_CODE
    , IS_ERROR
    , IS_WARNING
FROM M_EVENTS_IMPORT
WHERE
	SESSION_KEY = /*sessionKey*/''
ORDER BY IS_ERROR DESC, IS_WARNING DESC, ID asc
/*IF offset != null && sizeOfPage != null && sizeOfPage != -1 */
OFFSET /*offset*/ ROWS FETCH NEXT /*sizeOfPage*/ ROWS ONLY
/*END*/