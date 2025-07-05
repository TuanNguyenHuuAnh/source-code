package vn.com.unit.dts.web.rest.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;
import vn.com.unit.dts.exception.ErrorHint;

@Data
public class DtsApiResponse extends DtsBaseResponse implements Serializable {

    private static final long serialVersionUID = -2080447665304438927L;

//    private String time;

    private int statusCode;

    private String statusMessages;

//    private ErrorHint errorHint;

//    private ErrorHint hint;

    private String description;

//    private String hiddenDesc;

//    private long took;

    private Object data;

//    private String buildTime;

    @Autowired
    private ServletContext servletContext;

    public String getBuildTime() {
        String realPath = Paths.get("").toAbsolutePath().toString();
        File file = new File(realPath, "version_info.txt");
        if (file.exists()) {
            try {
                Scanner scan = new Scanner(file);
                String text = Optional.ofNullable(scan.nextLine()).orElse("");
//                setBuildTime(text);
                scan.close();
                return text;
            } catch (FileNotFoundException e) {

            }

        }
        return "";
    }

    public DtsApiResponse() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * Constructor for case Fail.
     *
     * @param codeStatus
     * @param messageStatus
     * @param description
     * @param took
     */
    public DtsApiResponse(int codeStatus, String messageStatus, ErrorHint errorHint, String description, long took) {
        super(codeStatus, messageStatus, null);
        this.statusCode = codeStatus;
        this.statusMessages = messageStatus;
        this.description = description;
        this.data = new DtsApiEmpty();
//        this.errorHint = errorHint;
//        this.took = took;
//        this.time = DtsDateFormatUtil.fullTimeZone();
//        this.setResultCode(DtsConstant.RESULT_CODE_SYSTEM_ERROR);
//        this.setResultDescription(description);
    }

    /**
     * Constructor for case Success.
     *
     * @param codeStatus
     * @param messageStatus
     * @param description
     * @param took
     * @param data
     */
    public DtsApiResponse(int codeStatus, String messageStatus, String description, long took, Object data) {
        super(codeStatus, messageStatus);
        this.statusCode = codeStatus;
        this.statusMessages = messageStatus;
        this.description = description;
        this.data = data;
//        this.took = took;
//        this.time = DtsDateFormatUtil.fullTimeZone();
//        this.setResultCode(0);
//        this.setResultDescription(DtsConstant.SUCCESS);
    }

    /**
     * Constructor for case Success.
     *
     * @param codeStatus
     * @param messageStatus
     * @param errorHint
     * @param description
     * @param took
     * @param data
     */
    public DtsApiResponse(int codeStatus, String messageStatus, ErrorHint errorHint, String description, long took,
            Object data) {
        super(codeStatus, messageStatus);
        this.statusCode = codeStatus;
        this.statusMessages = messageStatus;
        this.description = description;
        this.data = data;
//        this.errorHint = errorHint;
//        this.took = took;
//        this.time = DtsDateFormatUtil.fullTimeZone();
//        this.setResultCode(0);
//        this.setResultDescription(DtsConstant.SUCCESS);
    }

    public DtsApiResponse(int codeStatus, String messageStatus, ErrorHint hint, String description, Object data) {
        super(codeStatus, messageStatus);
        this.statusCode = codeStatus;
        this.statusMessages = messageStatus;
        this.description = description;
        this.data = data;
//        this.hint = hint;
//        this.time = DtsDateFormatUtil.fullTimeZone();
//        this.setResultCode(0);
//        this.setResultDescription(messageStatus);
    }

    /**
     * Constructor for case Success.
     *
     * @param codeStatus
     * @param description
     * @param data
     */
    public DtsApiResponse(int codeStatus, String description, Object data, DtsApiError error) {
        super(codeStatus, description, error);
        this.data = data;
    }

    /**
     * Constructor for case Fail.
     *
     * @param codeStatus
     * @param messageStatus
     * @param description
     * @param took
     */
    public DtsApiResponse(int codeStatus, String messageStatus, String description, String hiddenDesc, long took) {
        super(codeStatus, messageStatus, null);
        this.statusCode = codeStatus;
        this.statusMessages = messageStatus;
        this.description = description;
        this.data = new DtsApiEmpty();
//        this.hiddenDesc = hiddenDesc;
//        this.took = took;
//        this.time = DtsDateFormatUtil.fullTimeZone();
//        this.setResultCode(DtsConstant.RESULT_CODE_SYSTEM_ERROR);
//        this.setResultDescription(description);
    }

}
