select M.sUserID as USER_ID, M.sDeviceToken as DEVICE_TOKEN
from STG_ADP.APIToken M
inner join (select sUserID, max(dModifiedDate) as dModifiedDate
			from STG_ADP.APIToken
			group by sUserID) G
on  G.sUserID = M.sUserID
and G.dModifiedDate = M.dModifiedDate
where M.sDeviceToken is not null
and length(TRIM(TRANSLATE(replace(trim(M.sUserID), ' ', 'x'), '           ', '0123456789')))=0
and M.sUserID IN /*agentCodes*/()