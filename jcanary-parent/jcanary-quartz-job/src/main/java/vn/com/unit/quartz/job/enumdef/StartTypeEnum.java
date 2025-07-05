package vn.com.unit.quartz.job.enumdef;

import lombok.Getter;

@Getter
public enum StartTypeEnum {
    IMMEDIATE("IMMEDIATE"),
    ONE_TIME("ONE_TIME"),
    RECURRING("RECURRING");
    
    private String value;

    private StartTypeEnum(String value) {
        this.value = value;
    }
}
