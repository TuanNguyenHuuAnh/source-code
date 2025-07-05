package vn.com.unit.ep2p.enumdef;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EmulateChallengePersonalEnum {
    MEMONO("0")
    , CONTESTNAME("1")
    , EFFECTIVEDATE("2")
    , EXPIREDDATE("3");
    
    private String value;
    public String toString() {
        return value;
    }
}
