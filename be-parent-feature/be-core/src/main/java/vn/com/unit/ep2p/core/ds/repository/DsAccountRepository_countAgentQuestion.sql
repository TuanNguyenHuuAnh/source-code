SELECT count(*)
FROM JCA_ACCOUNT_QUESTION with (nolock)
WHERE
    1 = 1
    AND username = /*agentCode*/1
      AND QUESTION_CODE = /*questionCode*/''
      AND ANSWER = /*answer*/''