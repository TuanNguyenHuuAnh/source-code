package vn.com.unit.dts.web.rest.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DtsBaseResponse {
//    private int resultCode;
//    private String resultDescription;
    private DtsApiError error;

    public DtsBaseResponse(int resultCode, String resultDescription, DtsApiError error) {
//        this.resultCode = resultCode;
//        this.resultDescription = resultDescription;
        this.error = error;
    }

    public DtsBaseResponse(int resultCode, String resultDescription) {
//        this.resultCode = resultCode;
//        this.resultDescription = resultDescription;
    }
}
