/***************************************************************
 * @author vunt					
 * @date May 5, 2021	
 * @project mbal-core
 * @version 1.0 
 * @description 
 ***************************************************************/
package vn.com.unit.ep2p.core.enumdef;

import java.util.HashMap;
import java.util.Map;

public enum ProcessStatusBancasEnum {
	submitSubmit("submit","NTD nhập hồ sơ","btnSubmit","BC_000002","Đang chờ duyệt"),
	pendingEducateUpdate("pendingEducate","Pending đang đào tạo","btnUpdate","BC_000018","Pending chờ tốt nghiệp"),
	pendingInterviewReject("pendingInterview","Pending chờ phỏng vấn","btnReject","BC_000019","Phỏng vấn trượt"),
	pendingTrainingReject("pendingTraining","Đang chờ đi học","btnReject","BC_000013","Còn code Avicad"),
	waitingApproveApprove("waitingApprove","Đang chờ duyệt","btnApprove","BC_000004","Đang chờ phỏng vấn"),
	waitingApprovePending("waitingApprove","Đang chờ duyệt","btnPending","BC_000005","Pending chờ duyệt"),
	waitingApproveReject("waitingApprove","Đang chờ duyệt","btnReject","BC_000006","Loại Hồ sơ"),
	waitingEducateUpdate("waitingEducate","Đang đào tạo","btnUpdate","BC_000019","Đã tốt nghiệp"),
	waitingEducatePending("waitingEducate","Đang đào tạo","btnPending","BC_000017","Pending chờ tốt nghiệp"),
	waitingEducateReturn("waitingEducate","Đang đào tạo","btnReturn","BC_000012","Đang chờ đi học"),
	waitingEducateReject("waitingEducate","Đang đào tạo","btnReject","BC_000020","Trượt đào tạo"),
	waitingInterviewUpdate("waitingInterview","Đang chờ phỏng vấn","btnUpdate","BC_000007","Chờ check Avicad"),
	waitingInterviewPending("waitingInterview","Đang chờ phỏng vấn","btnPending","BC_000008","Pending chờ phỏng vấn"),
	waitingInterviewReject("waitingInterview","Đang chờ phỏng vấn","btnReject","BC_000009","Phỏng vấn trượt"),
	waitingTrainingUpdate("waitingTraining","Đang chờ đi học","btnUpdate","BC_000015","Đang đào tạo"),
	waitingTrainingSave("waitingTraining","Đang chờ đi học","btnSaveDraft","BC_000012","Đang chờ đi học"),
	waitingTrainingPending("waitingTraining","Đang chờ đi học","btnPending","BC_000011","Pending chờ check Avicad đi học"),
	waitingTrainingReject("waitingTraining","Đang chờ đi học","btnReject","BC_000013","Còn code Avicad"),
	waitingTrainingReserve("waitingTraining","Đang chờ đi học","btnReject","BC_000014","Bảo lưu"),
	waitingTrainingReserveReject("waitingTraining","Đang chờ đi học","btnReject","BC_000016","Không tham gia đào tạo"),
	waitingFinishedApprove("waitingFinished","Đã tốt nghiệp","btnApprove","BC_000021","Hoàn thành");


    private String key;
    private String processName;
    private String buttonCode;
    private String formStatus;
    private String formName;
    
    private ProcessStatusBancasEnum(String key,String processName,String buttonCode,String formStatus, String formName){
        this.key = key;
        this.processName = processName;
        this.buttonCode = buttonCode;
        this.formStatus = formStatus;
        this.formName = formName;
    }
    
   
    public String getKey(){
        return key;
    }
    public String getProcessName(){
        return processName;
    }
    public String getButtonCode(){
        return buttonCode;
    }
    public String getFormStatus(){
        return formStatus;
    }
    public String getFormName(){
        return formName;
    }
    private static final Map<String, ProcessStatusBancasEnum> mappings = new HashMap<>(ProcessStatusBancasEnum.values().length);

    static {
        for (ProcessStatusBancasEnum typeEnum : values()) {
            mappings.put(typeEnum.toString(), typeEnum);
        }
    }

    /**
     * resolveByValue
     * 
     * @param value
     * @return
     * @author vunt
     */
    public static ProcessStatusBancasEnum resolveByValue(String value) {
        return (value != null ? mappings.get(value) : null);
    }
    public String toString() {
        return key;
    }
}
