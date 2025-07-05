package vn.com.unit.ep2p.enumdef;

public enum PersonalClaimPolicyEnum {
      no("0")
      
      /** Số HĐBH*/
    , POLICYNO("1")
    
    /** Bên mua BH*/
    , PONAME("2")
    
    /** Người được bảo hiểm*/
    , LINAME("3")
    
    /** Số HS YCBT*/
    , CLAIMNO("4")
    
    /** Ngày mở HS*/
    , SCANNEDDATE("5")
    
    /** Loại yêu cầu bồi thường*/
    , CLAIMTYPE("6")
    
    /** Tình trạng*/
    , CLAIMSTATUS("7")
    
    /** Thông tin chờ bổ sung*/
    , DOCUMENTNAME("8")
    
    /** Ngày hết hạn bổ sung*/
    , EXPIREDDATE("9")
    
    /** Ngày có kết quả bồi thường*/
    , APPROVEDATE("10")
    
    /** Số tiền bồi thường*/
    , TOTALAPPROVEAMOUNT("11")
    
    /** Bổ sung quyền lợi nhận thanh toán*/
    , APPROVEDREMARK("12")
    
    /** Lý do từ chối*/
    , REJECTEDREMARK("13")
    
    ;
    private String value;

    private PersonalClaimPolicyEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
