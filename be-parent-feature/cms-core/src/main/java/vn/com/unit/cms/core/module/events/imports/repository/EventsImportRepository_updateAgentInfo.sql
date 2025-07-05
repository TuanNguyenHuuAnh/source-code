UPDATE M_EVENTS_IMPORT
SET NAME = /*agent.agentName*/
, GENDER = /*agent.gender*/
, EMAIL = /*agent.emailAddress1*/
, TEL = /*agent.mobilePhone*/
, ADDRESS = /*agent.fullAddress*/
, ID_NUMBER = /*agent.idNumber*/
, TERRITORRY = /*agent.territorry*/
, AREA = /*agent.area*/
, REGION = /*agent.region*/
, OFFICE = /*agent.office*/
, POSITION = /*agent.position*/
WHERE
    ID = /*id*/''