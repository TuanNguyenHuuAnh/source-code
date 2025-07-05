select 
lang.title as title
from m_introduction_language lang
where 
lang.M_INTRODUCTION_ID = /*introductionId*/
and lang.M_LANGUAGE_CODE = UPPER(/*languageCode*/)
and lang.DELETE_BY is null
;