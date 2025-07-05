package vn.com.unit.fcm.enumdef;


public enum FcmNotificationParameter {
    SOUND("default"),
    COLOR("#FF0000");

    private String value;

    FcmNotificationParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
