/***************************************************************
 * @author vunt
 * @date Apr 29, 2021
 * @project mbal-core
 * @version 1.0 
 * @description 
 ***************************************************************/
package vn.com.unit.ep2p.core.enumdef;

import java.util.HashMap;
import java.util.Map;

public enum ProcessStatusAgencyEnum {
	stepSaveDraft2("stepSaveDraft2","NTD nhập hồ sơ","btnSubmit","AG_000015","Đang xác nhận hồ sơ và danh tính"),
	stepSaveDraft1("stepSaveDraft1","Chờ ứng viên xác thực","btnSubmit","AG_000003","Chờ check EKYC từ xa"),
	stepPendingApproved1("stepPendingApproved1","Chờ NTD kiểm tra hồ sơ","btnSubmit","AG_000003","Chờ check EKYC từ xa"),
	stepPendingApproved2("stepPendingApproved2","Chờ quản lý trực tiếp","btnSubmit","AG_000009","Chờ Quản lý trực tiếp xác nhận hồ sơ"),
	stepPendingApproved31Submit("stepPendingApproved31","Chờ QL cấp cao 1 phỏng vấn","btnSubmit","AG_000007","Chờ Quản lý cấp cao 1 phỏng vấn"),
	stepPendingApproved31("stepPendingApproved31","Chờ QL cấp cao 1 phỏng vấn","btnApprove","AG_000007","Chờ Quản lý cấp cao 1 phỏng vấn"),
	stepPendingApproved32("stepPendingApproved32","Chờ QL cấp cao 2 phỏng vấn","btnApprove","AG_000008","Chờ Quản lý cấp cao 2 phỏng vấn"),
	stepPeddingApproveLv41("stepPeddingApproveLv41","Đang chờ AR kiểm tra hồ sơ","btnApprove","AG_000006","Chờ phòng tuyển dụng xác nhận hồ sơ"),
	stepPeddingApproveLv42("stepPeddingApproveLv42","Đang chờ AS kiểm tra hồ sơ","btnApprove","AG_000004","Chờ phòng hỗ trợ đại lý xác nhận hồ sơ"),
	stepPeddingApproveLv5("stepPeddingApproveLv5","Đang chờ AS xử lý hồ sơ","btnApprove","AG_000005","Chờ phòng hỗ trợ đại lý xử lý hồ sơ"),
	stepReturn("stepReturn","Chờ bổ sung hồ sơ","btnReturn","AG_000002","Chờ bổ sung thông tin hồ sơ"),
	stepPendingApproved1Return("stepPendingApproved1","Chờ bổ sung hồ sơ","btnReturn","AG_000002","Chờ bổ sung thông tin hồ sơ"),
//	stepPeddingApproveLv5("stepPeddingApproveLv5","Đang chờ AS xử lý hồ sơ","Import","AG_000010","Chưa tham dự COP"),
//	stepPeddingApproveLv5("stepPeddingApproveLv5","Đang chờ AS xử lý hồ sơ","Import","AG_000017","Kiểm tra Avicad"),
//	stepPeddingApproveLv5("stepPeddingApproveLv5","Đang chờ AS xử lý hồ sơ","Import","AG_000023","Tạm hoãn"),
//	stepPeddingApproveLv5("stepPeddingApproveLv5","Đang chờ AS xử lý hồ sơ","Import","AG_000014","Đang chờ tham dự học"),
//	stepPeddingApproveLv5("stepPeddingApproveLv5","Đang chờ AS xử lý hồ sơ","Import","AG_000020","Không hoàn thành khóa học SP"),
//	stepPeddingApproveLv5("stepPeddingApproveLv5","Đang chờ AS xử lý hồ sơ","Import","AG_000021","Không tham dự khóa học"),
//	stepPeddingApproveLv5("stepPeddingApproveLv5","Đang chờ AS xử lý hồ sơ","Import","AG_000019","Không đủ điều kiện tham dự thi"),
//	stepPeddingApproveLv5("stepPeddingApproveLv5","Đang chờ AS xử lý hồ sơ","Import","AG_000022","Không tham dự thi"),
//	stepPeddingApproveLv5("stepPeddingApproveLv5","Đang chờ AS xử lý hồ sơ","Import","AG_000024","Thi Rớt MBSU & MBFS"),
//	stepPeddingApproveLv5("stepPeddingApproveLv5","Đang chờ AS xử lý hồ sơ","Import","AG_000025","Thi Rớt MBSU"),
//	stepPeddingApproveLv5("stepPeddingApproveLv5","Đang chờ AS xử lý hồ sơ","Import","AG_000018","Kiểm tra Avicad cấp code"),
//	stepPeddingApproveLv5("stepPeddingApproveLv5","Đang chờ AS xử lý hồ sơ","Import","AG_000013","Đang chờ cấp mã số"),
	stepPeddingApproveLv5Finish("stepPeddingApproveLv5","Đang chờ AS xử lý hồ sơ","Import","AG_000011","Đã có mã số"),
//	stepPeddingApproveLv5("stepPeddingApproveLv5","Đang chờ AS xử lý hồ sơ","Import","AG_000012","Đã có mã số nhưng chưa có quyền bán SP"),
//	stepPeddingApproveLv5("stepPeddingApproveLv5","Đang chờ AS xử lý hồ sơ","Import","AG_000002","Chờ bổ sung thông tin hồ sơ"),
	Finished("999","Finished","btnApprove","AG_000016","Hoàn thành"),
	Reject("998","Reject","btnReject","","Từ chối");


	private String key;
    private String processName;
    private String buttonCode;
    private String formStatus;
    private String formName;
    
    private ProcessStatusAgencyEnum(String key,String processName,String buttonCode,String formStatus, String formName){
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
    private static final Map<String, ProcessStatusAgencyEnum> mappings = new HashMap<>(ProcessStatusAgencyEnum.values().length);

    static {
        for (ProcessStatusAgencyEnum typeEnum : values()) {
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
    public static ProcessStatusAgencyEnum resolveByValue(String value) {
        return (value != null ? mappings.get(value) : null);
    }
    public String toString() {
        return key;
    }
}