package vn.com.unit.ep2p.enumdef;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReportBusinessResultPremiumEnum {
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
    , K2STR("12")//%K2
    , K2PLUSSTR("13")//%K2+
    //detail
    , LASTPOLICYCOUNTRECEIVED("14")//TỔNG HỒ SƠ NOP VAO
    , LASTFYPRECEIVED("15")//TỔNG FYP
    , LASTPOLICYCOUNTISSUED("16")////TỔNG HỢP ĐỒNG PHAT HANH
    , LASTFYPISSUED("17")//TỔNG FYP
    , LASTPOLICYCOUNT("18")//TỔNG HỢP ĐỒNG PHAT HANH THUAN
    , LASTFYP("19")//TỔNG FYP PHAT HANH THUAN
    , LASTRYP("20")//TỔNG RYP
    ;
    private String value;

    public String toString() {
        return value;
    }
}
