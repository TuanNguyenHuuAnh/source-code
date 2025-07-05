SELECT COUNT(*) 
FROM STG_WF_AA.PDBFolder fld
     INNER JOIN STG_WF_AA.PDBDocumentContent ctn
        ON fld.FolderIndex = ctn.ParentFolderIndex
     INNER JOIN STG_WF_AA.PDBDocument doc
        ON ctn.DocumentIndex = doc.DocumentIndex
     INNER JOIN STG_WF_AA.EXT_AGENCYADMIN ag
        ON (
                  fld.NAME = ag.WORKFLOW_ID
             AND NVL(ag.ImageChk_Status, '') NOT IN ('Invalid', 'Reject') 
             AND ag.agentcode = /*agentCode*/  )
WHERE doc.NAME = 'AgentTaxConfirmation'
      AND YEAR(doc.createdDatetime) = YEAR(CURRENT DATE)