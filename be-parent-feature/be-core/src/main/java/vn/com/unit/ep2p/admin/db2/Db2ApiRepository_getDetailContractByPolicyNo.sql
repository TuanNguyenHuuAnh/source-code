SELECT
    p.STATUS                     as    pol_Status_Txt             --Trạng thái hiệu lực
     , p.POL_BILL_MODE_CD        as    pol_Bill_Mode_Cd           --Định kỳ đóng phí
     , p.POL_MPREM_AMT           as    pol_Mprem_Amt              --Phí định kỳ/cơ bản định kỳ
     , p.TOTAL_PREMIUM_PAID      as    total_Deposit             --Tổng phí bảo hiểm đã đóng
     , p.POL_BASE_FACE_AMT       as    pol_Base_Face_Amt           --Số tiền bảo hiểm
     , p.POL_SNDRY_AMT           as    pol_Sndry_amt               --Phí dự tính định kỳ
     , p.ZPRX_UNPAID_PREM_AMT        --Phí cơ bản các kỳ trước chưa đóng
     , p.POL_PREM_SUSP_AMT
     , p.POL_MISC_SUSP_AMT           --Phí đóng trước cho kỳ tới: (tính toán bằng công thức) D_POLICY.POL_PREM_SUSP_AMT + D_POLICY.POL_MISC_SUSP_AMT
     , p.POL_CEAS_DT                 --Ngay mat hieu luc/dao han
     , p.POL_LAPSE_START_DT          --Ngày sẽ mất hiệu lực
     , p.SERV_AGT_ASIGN_DT           --Ngày phân công
     , jca.CAT_OFFICIAL_NAME          --Hình thức phân công
FROM RPT_ODS.D_POLICY p
         INNER JOIN STG_ING.TZAGH TZ
                    ON TZ.POL_ID = p.POLICY_KEY
         INNER JOIN STG_DMS.JCA_CONSTANT_DISPLAY  JCA
                    ON TZ.ASSIGN_CD = JCA.CAT
WHERE jca.TYPE = 'REASON_TRANSFER_POLICY'
  AND p.POLICY_KEY = /*policyNo*/
ORDER BY TZ.AGT_NEW_ID