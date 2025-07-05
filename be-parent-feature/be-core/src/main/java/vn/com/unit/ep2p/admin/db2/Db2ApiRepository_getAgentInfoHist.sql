select g.AGENT_GROUP, D.O_CODE ORG_CODE FROM RPT_ODS.DS_VW_AGENT_GROUP g
                        inner join RPT_ODS.F_SNAPSHOT_AGENT_HIERARCHY D ON G.AGENT_TYPE=D.LV3_AGENTTYPE
                         WHERE CAST(D.AGENT_KEY AS VARCHAR(32))=/*agentCode*/'324647' and LEFT(D.SNAPSHOT_DATE_KEY, 6) = /*dateKey*/
                         FETCH FIRST 1 ROWS ONLY;