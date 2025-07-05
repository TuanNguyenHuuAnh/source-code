/**
 * @author TaiTM
 */
package vn.com.unit.ep2p.core.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.ers.entity.AdoFamilyInfor;
import vn.com.unit.ep2p.core.ers.entity.ErsCandidatePlan;
import vn.com.unit.ep2p.core.ers.entity.ErsCandidateQA;
import vn.com.unit.ep2p.core.ers.entity.ErsInterviewResult;
import vn.com.unit.ep2p.core.ers.entity.ErsWorkingExperience;
import vn.com.unit.ep2p.core.res.dto.ErsCandidateAttachDocumentDto;

/**
 * @author TaiTM
 *
 */
@Getter
@Setter
public class RecruitmentPdfDto {
    private Long id;

    private String channel;

    private String applyForPosition;

    private String positionName;

    private int optionSupport;
    
    private String option1;
    
    private String option2;

    private String regionName;

    private String provinceName;

    private String agentCode;

    private String bpCode;

    private Date dateAppointed;

    private Date dateTerminate;

    private String agentStatus;

    private int reinsFlag;
    
    private String reins;

    private String partnerMic;

    private String idType;

    private String idNo;

    private Date idDateOfIssue;

    private String idPlaceOfIssue;

    private String idPlaceOfIssueName;
    
    private String otherIdNo;

    private Date otherIdDateOfIssue;

    private String otherIdPlaceOfIssue;

    private String otherIdPlaceName;

    private String candidateName;

    private String gender;

    private String genderName;

    private Date dob;

    private String pob;

    private String pobName;

    private String email;

    private String mobile;

    private String nationality;

    private String nation;

    private String educational;
    
    private String educationalName;

    private String carrerCurrent;
    
    private String carrerCurrentName;

    private String maritalStatus;
    
    private String maritalStatusName;

    private String taxCode;

    private String permanentAddress;

    private String permanentNest;

    private String permanentProvince;

    private String permanentProvinceName;

    private String permanentDistrict;

    private String permanentDistrictName;

    private String permanentWard;

    private String permanentWardName;

    private int sameAddress;

    private String currentAddress;

    private String currentNest;

    private String currentProvince;

    private String currentProvinceName;

    private String currentDistrict;

    private String currentDistrictName;

    private String currentWard;

    private String currentWardName;

    private String contactFullName;

    private String contactPhone;

    private String contactRelationship;

    private String accountName;

    private String accountNumber;

    private String bankName;

    private String bankProvince;
    
    private String bankProvinceName;

    private String bankBranch;

    private String bankBranchName;

    private String classType;

    private String onlineOffline;

    private String classProvince;

    private String classProvinceName;

    private String classCode;

    private String classPlace;

    private Date beginningDate;

    private Date completedDate;

    private String examPlace;

    private Date examDate;

    private Date firstClassDate;

    private String classNote;

    private int recruiterType;

    private String recruiterTypeName;

    private Long recruiterCode;

    private String recruiterIdNo;

    private String recruiterName;

    private Long recruiterAdCode;

    private String recruiterAdName;

    private String recruiterAdPosition;

    private String recruiterPosition;

    private int managerType;

    private String managerTypeName;

    private Long managerCode;

    private String managerIdNo;

    private String managerName;

    private Long managerAdCode;

    private String managerAdName;

    private String managerAdPosition;

    private String managerPosition;

    private int managerIndirectType;

    private String managerIndirectTypeName;

    private Long managerIndirectCode;

    private String managerIndirectIdNo;

    private String managerIndirectName;

    private Long managerIndirectAdCode;

    private String managerIndirectAdName;

    private String managerIndirectAdPosition;

    private String managerIndirectPosition;
    
    private String managerNameApprove;
    
    private Long managerCodeApprove;

    private int checkEkycStatus;

    private int ekycOption;
    
    private String ekycOptionString;

    private Date ekycSendMailTime;

    private int ekycResult;

    private String ekycStatus;

    private int checkInterviewStatus;

    private Date interviewSendMailTime;
    
    private String interviewAgentCode;
    
    private String interviewName;

    private String interviewComment;

    private String interview2Comment;

    private String interview2AgentCode;

    private String interview2Name;

    private String interviewPoint;

    private String interview2Point;

    private String interviewStatus;

    private String interview2Status;

    private String avicadStatus;

    private int checkAvicadStatus;

    private String refAttachement;

    private Long companyId;

    private Long formId;

    private String docUuid;

    private String formStatus;

    private String fieldUser1;

    private String fieldUser2;

    private String fieldUser3;

    private String fieldUser4;

    private String fieldUser5;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedBy;

    private Date deletedDate;

    private int deletedFlag;

    private String otherPosition;

    private String formStatusCode;

    private Date submitInforDate;

    private String secretKey;

    private Date secretDate;

    private String otp;

    private Long repoId;

    private String idFile1;

    private String idFile2;

    private String personalImage;

    private String ekycImage;

    private String ekycImageUrl;
    
    private String familyFlag;
    
    private String attachFlag;

    private List<ErsCandidatePlan> plan12MonthAM;

    private List<ErsCandidatePlan> plan12MonthAD;

    private List<ErsCandidateAttachDocumentDto> fileAttachment;

    private List<ErsWorkingExperience> listWorkingExperience;

    private List<AdoFamilyInfor> listFamilyInfor;

    private List<ErsCandidateQA> surveyQuestion;

    private List<ErsInterviewResult> interviewQuestion;

    private Map<String, List<ErsInterviewResult>> interviewResultList;
    
    private String hasRelationInsu;
}
