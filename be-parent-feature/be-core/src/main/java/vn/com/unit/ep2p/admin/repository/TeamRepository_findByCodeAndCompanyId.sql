select team.* ,
	team.ID AS TEAM_ID
from jca_team team
where team.DELETED_ID = 0
   	AND UPPER (team.code) = UPPER (/*code*/)
	/*IF companyId != null*/
	AND (team.company_id is null or team.company_id = /*companyId*/)
	/*END*/