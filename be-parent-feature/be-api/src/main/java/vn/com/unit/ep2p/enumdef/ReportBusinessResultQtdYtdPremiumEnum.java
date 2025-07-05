package vn.com.unit.ep2p.enumdef;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReportBusinessResultQtdYtdPremiumEnum {
    //PREMIUM
    GANAME("0")
    , BRANCHNAME("1")
    , POLICYCOUNTRECEIVED("2")//NOP VAO
    , FYPRECEIVED("3")
    , POLICYCOUNTISSUED("4")//PHAT HANH
    , FYPISSUED("5")
    , POLICYCOUNT("6")//PHAT HANH THUAN
    , FYP("7")
    , POLICYCOUNTCANCEL("8")//HUY
    , FYPCANCEL("9")
    , RFYP("10")//ĐC/TÁI TỤC FYP
    , RYP("11")//RYP
    //detail
    , LASTPOLICYCOUNTRECEIVED("12")//TỔNG HỒ SƠ NOP VAO
    , LASTFYPRECEIVED("13")//TỔNG FYP
    , LASTPOLICYCOUNTISSUED("14")////TỔNG HỢP ĐỒNG PHAT HANH
    , LASTFYPISSUED("15")//TỔNG FYP
    , LASTPOLICYCOUNT("16")//TỔNG HỢP ĐỒNG PHAT HANH THUAN
    , LASTFYP("17")//TỔNG FYP PHAT HANH THUAN
    , LASTRYP("18")//TỔNG RYP
    ;
    private String value;

    public String toString() {
        return value;
    }
}
