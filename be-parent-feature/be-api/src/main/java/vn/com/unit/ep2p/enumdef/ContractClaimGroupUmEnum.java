package vn.com.unit.ep2p.enumdef;

public enum ContractClaimGroupUmEnum {
    NO("0")
    /** Tư vấn tài chính*/
    , AGENTALL("1")
    /** Số HĐBH*/
    , POLICYNO("2")
    /** Bên mua bảo hiểm*/
    , PONAME("3")
    /** Người được bảo hiểm*/
    , LINAME("4")
    /** Số hồ sơ yêu cầu bồi thường*/
    , CLAIMNO("5")
    /** Ngày mở mở hồ sơ*/
    , SCANDATE("6")
    /** Loại yêu cầu bồi thường*/
    , CLAIMTYPE("7")
    /** Tình trạng*/
    , STATUSVN("8")
    /** Thông tin chờ bổ sung*/
    , DOCUMENTNAME("9")
    /** Ngày hết hạn bổ sung*/
    , EXPIREDDATE("10")
    /** Ngày có kết quả bồi thường*/
    , APPROVEDATE("11")
    /** Số tiền bồi thường*/
    , TOTALAPPROVEAMOUNT("12")
    /** Bổ sung quyền lợi nhận thanh toán*/
    , APPROVEDREMARK("13")
    /** Lý do từ chối*/
    , REJECTEDREMARK("14")
    ;
    private String value;
    
    private ContractClaimGroupUmEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
