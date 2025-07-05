package vn.com.unit.ep2p.enumdef;

public enum ContractClaimGroupBmEnum {
    NO("0")
    /** Quản lý*/
    , PARENTALL("1")
    /** Tư vấn tài chính*/
    , AGENTALL("2")
    /** Số HĐBH*/
    , POLICYNO("3")
    /** Bên mua bảo hiểm*/
    , PONAME("4")
    /** Người được bảo hiểm*/
    , LINAME("5")
    /** Số hồ sơ yêu cầu bồi thường*/
    , CLAIMNO("6")
    /** Ngày mở mở hồ sơ*/
    , SCANDATE("7")
    /** Loại yêu cầu bồi thường*/
    , CLAIMTYPE("8")
    /** Tình trạng*/
    , STATUSVN("9")
    /** Thông tin chờ bổ sung*/
    , DOCUMENTNAME("10")
    /** Ngày hết hạn bổ sung*/
    , EXPIREDDATE("11")
    /** Ngày có kết quả bồi thường*/
    , APPROVEDATE("12")
    /** Số tiền bồi thường*/
    , TOTALAPPROVEAMOUNT("13")
    /** Bổ sung quyền lợi nhận thanh toán*/
    , APPROVEDREMARK("14")
    /** Lý do từ chối*/
    , REJECTEDREMARK("15")
    ;
    private String value;
    
    private ContractClaimGroupBmEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
