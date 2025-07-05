UPDATE JCA_CONSTANT SET 
    KIND = /*entity.kind*/''
    , CODE = /*entity.code*/''
    , ACTIVED = /*entity.actived*/''
    , DISPLAY_ORDER = /*entity.displayOrder*/''
    , NAME = /*entity.name*/''
    , LANG_ID = /*entity.langId*/''
    , LANG_CODE = /*entity.langCode*/''
    , UPDATED_ID =/*entity.updatedId*/''
    , UPDATED_DATE = GETDATE()
WHERE
    GROUP_CODE = /*oldGroupCode*/1
    AND KIND = /*oldKind*/1
    AND CODE = /*oldCode*/
    AND LANG_ID = /*entity.langId*/''