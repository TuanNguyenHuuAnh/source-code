UPDATE JCA_GROUP_CONSTANT SET NAME = /*entity.name*/''
, UPDATED_ID = /*entity.updatedId*/''
, UPDATED_DATE = GETDATE()
where CODE = /*entity.code*/'' and LANG_CODE = /*entity.langCode*/''
