UPDATE JCA_ROLE_FOR_POSITION
SET DEL_FLG = 1, DELETED_ID = /*userDeleteId*/, DELETED_DATE = /*systemDate*/
WHERE id IN /*ids*/()