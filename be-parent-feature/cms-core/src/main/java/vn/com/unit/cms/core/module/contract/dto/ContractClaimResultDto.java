package vn.com.unit.cms.core.module.contract.dto;

import java.math.BigDecimal;
import java.util.Date;

import vn.com.unit.ep2p.utils.AppStringUtils;

public class ContractClaimResultDto {
	private int no;
	/** Số HĐBH */
	private String policyKey;

	/**  */
	private String poId;

	/** Bên mua bảo hiểm */
	private String poName;

	/** Tư vấn tài chính */
	private String servicingAgentKey;

	/** Tên Tư vấn tài chính */
	private String agentName;

	/** Số hợp đồng */
	private String policyno;

	/** Số HSYCBH */
	private String claimno;

	private String insuredclientid;

	/** Người được BH */
	private String liName;

	/** Ngày mở hồ sơ */
	private Date scanneddate;

	private Date modifiedondate;

	/** Loại HSYCBH */
	private String claimtype;

	/** Tình trạng ID */
	private String statusid;

	/** Tình trạng */
	private String claimstatus;

	/** Ngày có kết quả bồi thường */
	private Date approvedate;

	/** Bổ sung chứng từ nhận quyền lợi thanh toán */
	private String approvedremark;

	/** Lý do từ chối */
	private String rejectedremark;

	/** Sô tiền bổi thường */
	private BigDecimal totalapproveamount;
	
	/** Sô tiền bổi thường */
	private String agentType;
	
	/** Ngày hết hạn bổ sung */
	private Date expireddate;

	/** Thông tin chờ bổ sung */
	private String documentname;

	private Integer isShare;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getPolicyKey() {
		return AppStringUtils.formatPolicyNumber(9, policyKey);
	}

	public void setPolicyKey(String policyKey) {
		this.policyKey = policyKey;
	}

	public String getPoId() {
		return poId;
	}

	public void setPoId(String poId) {
		this.poId = poId;
	}

	public String getPoName() {
		return poName;
	}

	public void setPoName(String poName) {
		this.poName = poName;
	}

	public String getServicingAgentKey() {
		return servicingAgentKey;
	}

	public void setServicingAgentKey(String servicingAgentKey) {
		this.servicingAgentKey = servicingAgentKey;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getPolicyno() {
		return AppStringUtils.formatPolicyNumber(9, policyno);
	}

	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}

	public String getClaimno() {
		return claimno;
	}

	public void setClaimno(String claimno) {
		this.claimno = claimno;
	}

	public String getInsuredclientid() {
		return insuredclientid;
	}

	public void setInsuredclientid(String insuredclientid) {
		this.insuredclientid = insuredclientid;
	}

	public String getLiName() {
		return liName;
	}

	public void setLiName(String liName) {
		this.liName = liName;
	}

	public Date getScanneddate() {
		return scanneddate;
	}

	public void setScanneddate(Date scanneddate) {
		this.scanneddate = scanneddate;
	}

	public Date getModifiedondate() {
		return modifiedondate;
	}

	public void setModifiedondate(Date modifiedondate) {
		this.modifiedondate = modifiedondate;
	}

	public String getClaimtype() {
		return claimtype;
	}

	public void setClaimtype(String claimtype) {
		this.claimtype = claimtype;
	}

	public String getStatusid() {
		return statusid;
	}

	public void setStatusid(String statusid) {
		this.statusid = statusid;
	}

	public String getClaimstatus() {
		return claimstatus;
	}

	public void setClaimstatus(String claimstatus) {
		this.claimstatus = claimstatus;
	}

	public Date getApprovedate() {
		return approvedate;
	}

	public void setApprovedate(Date approvedate) {
		this.approvedate = approvedate;
	}

	public String getApprovedremark() {
		return approvedremark;
	}

	public void setApprovedremark(String approvedremark) {
		this.approvedremark = approvedremark;
	}

	public String getRejectedremark() {
		return rejectedremark;
	}

	public void setRejectedremark(String rejectedremark) {
		this.rejectedremark = rejectedremark;
	}

	public BigDecimal getTotalapproveamount() {
		return totalapproveamount;
	}

	public void setTotalapproveamount(BigDecimal totalapproveamount) {
		this.totalapproveamount = totalapproveamount;
	}

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	public Date getExpireddate() {
		return expireddate;
	}

	public void setExpireddate(Date expireddate) {
		this.expireddate = expireddate;
	}

	public String getDocumentname() {
		return documentname;
	}

	public void setDocumentname(String documentname) {
		this.documentname = documentname;
	}

	public Integer getIsShare() {
		return isShare;
	}

	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
	}
	
}

