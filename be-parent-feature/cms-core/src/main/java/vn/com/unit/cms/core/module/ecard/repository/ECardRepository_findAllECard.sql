select * from dbo.M_ECARD
WHERE ENABLED = 1
/*IF channel == null || channel == ''*/
AND isnull(CHANNEL, 'AG') = 'AG'
/*END*/
/*IF channel != null*/
AND isnull(CHANNEL, 'AG') = /*channel*/
/*END*/
AND DELETE_BY is null