package vn.com.unit.ep2p.enumdef;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReportBusinessResultManpowerEnum {
    GANAME("0")
    , BRANCHNAME("1")
    //MANPOWER
    , countBmAgentcode("2")
    , countUmTypeAgentcode("3")
    , countPumTypeAgentcode("4")
    , countFcTypeAgentcode("5")
    , countSaTypeAgentcode("6")
    , COUNTNEWRECRUITMENT("7")//sl tuyen dung
    , COUNTREINSTATE("8")//recover
    , COUNTACTIVE("9")//fc hoat dong
    , COUNTNEWRECRUITMENTACTIVE("10")
    , COUNTSCHEMEFC("11")//schema
    , COUNTPFCFC("12")//nang dong
    //detail
    , LASTCOUNTNEWRECRUITMENT("13")//Tuyển dụng
    , LASTCOUNTREINSTATE("14")//Khôi phục mã số
    , LASTCOUNTNEWRECRUITMENTACTIVE("15")//Số lượng TV mới hoạt động
    , LASTCOUNTACTIVE("16") //Số lượng FC hoạt động
    ;
    private String value;

    public String toString() {
        return value;
    }
}
