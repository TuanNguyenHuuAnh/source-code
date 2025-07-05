package vn.com.unit.ep2p.core.res.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.ers.dto.ValueAutocompleteDto;
import vn.com.unit.ep2p.core.ers.entity.AdoFamilyInfor;
import vn.com.unit.ep2p.core.ers.entity.ErsCandidatePlan;
import vn.com.unit.ep2p.core.ers.entity.ErsCandidateQA;
import vn.com.unit.ep2p.core.ers.entity.ErsInterviewResult;
import vn.com.unit.ep2p.core.ers.entity.ErsWorkingExperience;

@Getter
@Setter

/**
 * 
 * update : nhung cho nao dung select box se tra ve dang {title :'', value:''} nen se dung HashMap hoac ValueAutocompleteDto
 *
 */
public class RecruitmentCandidateResultActionRes extends DocumentActionReq{
	
//	private static final String DATE_PATTERN = "dd/MM/yyyy";
    protected static final String TIME_ZONE = "Asia/Saigon";
	
    private Long id;

    /** Column: channel VARCHAR(10) */
    private String channel;

    /** Column: applyForPosition VARCHAR(255) */
    private ValueAutocompleteDto applyForPosition;

    /** Column: positionName VARCHAR(255) */
    private String positionName;

    /** Column: optionSupport TINYINT(3) */
    private String optionSupport;

    /** Column: regionName VARCHAR(10) */
    private ValueAutocompleteDto regionName;

    /** Column: provinceName NVARCHAR(255) */
    private ValueAutocompleteDto provinceName;

    /** Column: agentCode VARCHAR(10) */
    private String agentCode;

    /** Column: bpCode VARCHAR(10) */
    private String bpCode;

    /** Column: dateAppointed DATE(10) */
    @Nullable
    private Date dateAppointed;

    /** Column: dateTerminate DATE(10) */
    @Nullable
    private Date dateTerminate;

    /** Column: agentStatus NVARCHAR(10) */
    private String agentStatus;

    /** Column: reinsFlag TINYINT(3) */
    private Boolean reinsFlag;

    /** Column: partnerMic NVARCHAR(200) */
    private ValueAutocompleteDto partnerMic;

    /** Column: idType VARCHAR(10) */
    private ValueAutocompleteDto idType;

    /** Column: idNo VARCHAR(50) */
    private String idNo;

    /** Column: idDateOfIssue DATE(10) */
    @Nullable
    private Date idDateOfIssue;

    /** Column: idPlaceOfIssue VARCHAR(10) */
    private ValueAutocompleteDto idPlaceOfIssue;

    /** Column: idPlaceOfIssueName NVARCHAR(255) */
    private String idPlaceOfIssueName;

    /** Column: otherIdNo VARCHAR(10) */
    private String otherIdNo;

    /** Column: otherIdDateOfIssue DATE(10) */
    @Nullable
    private Date otherIdDateOfIssue;

    /** Column: otherIdPlaceOfIssue VARCHAR(10) */
    private ValueAutocompleteDto otherIdPlaceOfIssue;

    /** Column: otherIdPlaceName NVARCHAR(255) */
    private String otherIdPlaceName;

    /** Column: candidateName NVARCHAR(255) */
    private String candidateName;

    /** Column: gender VARCHAR(2) */
    private String gender;

    /** Column: genderName VARCHAR(50) */
    private String genderName;

    /** Column: dob DATE(10) */
    @Nullable
    private Date dob;

    /** Column: pob VARCHAR(10) */
    private ValueAutocompleteDto pob;

    /** Column: pobName NVARCHAR(255) */
    private String pobName;

    /** Column: email VARCHAR(255) */
    private String email;

    /** Column: mobile VARCHAR(20) */
    private String mobile;

    /** Column: nationality NVARCHAR(255) */
    private String nationality;

    /** Column: nation NVARCHAR(255) */
    private String nation;

    /** Column: educational VARCHAR(255) */
    private ValueAutocompleteDto educational;

    /** Column: carrerCurrent VARCHAR(255) */
    private ValueAutocompleteDto carrerCurrent;

    /** Column: maritalStatus VARCHAR(10) */
    private ValueAutocompleteDto maritalStatus;

    /** Column: taxCode VARCHAR(50) */
    private String taxCode;

    /** Column: permanentAddress NVARCHAR(255) */
    private String permanentAddress;

    /** Column: permanentNest NVARCHAR(255) */
    private String permanentNest;

    /** Column: permanentProvince VARCHAR(10) */
    private ValueAutocompleteDto permanentProvince;

    /** Column: permanentProvinceName NVARCHAR(255) */
    private String permanentProvinceName;

    /** Column: permanentDistrict VARCHAR(10) */
    private ValueAutocompleteDto permanentDistrict;

    /** Column: permanentDistrictName NVARCHAR(255) */
    private String permanentDistrictName;

    /** Column: permanentWard VARCHAR(10) */
    private ValueAutocompleteDto permanentWard;

    /** Column: permanentWardName NVARCHAR(255) */
    private String permanentWardName;

    /** Column: sameAddress TINYINT(3) */
    private Boolean sameAddress;

    /** Column: currentAddress NVARCHAR(255) */
    private String currentAddress;

    /** Column: currentNest NVARCHAR(255) */
    private String currentNest;

    /** Column: currentProvince VARCHAR(10) */
    private ValueAutocompleteDto currentProvince;

    /** Column: currentProvinceName NVARCHAR(255) */
    private String currentProvinceName;

    /** Column: currentDistrict VARCHAR(10) */
    private ValueAutocompleteDto currentDistrict;

    /** Column: currentDistrictName NVARCHAR(255) */
    private String currentDistrictName;

    /** Column: currentWard VARCHAR(10) */
    private ValueAutocompleteDto currentWard;

    /** Column: currentWardName NVARCHAR(255) */
    private String currentWardName;

    /** Column: contactFullName NVARCHAR(255) */
    private String contactFullName;

    /** Column: contactPhone VARCHAR(10) */
    private String contactPhone;

    /** Column: contactRelationship VARCHAR(10) */
    private ValueAutocompleteDto contactRelationship;

    /** Column: accountName NVARCHAR(255) */
    private String accountName;

    /** Column: accountNumber VARCHAR(50) */
    private String accountNumber;

    /** Column: bankName NVARCHAR(255) */
    private ValueAutocompleteDto bankName;

    /** Column: bankProvince NVARCHAR(255) */
    private ValueAutocompleteDto bankProvince;

    /** Column: bankBranch VARCHAR(255) */
    private ValueAutocompleteDto bankBranch;

    /** Column: bankBranchName NVARCHAR(255) */
    private String bankBranchName;

    /** Column: classType VARCHAR(255) */
    private ValueAutocompleteDto classType;

    /** Column: onlineOffline VARCHAR(10) */
    private String onlineOffline;

    /** Column: classProvince VARCHAR(10) */
    private ValueAutocompleteDto classProvince;

    /** Column: classProvinceName NVARCHAR(255) */
    private String classProvinceName;

    /** Column: classCode NVARCHAR(100) */
    private ValueAutocompleteDto classCode;

    /** Column: classPlace NVARCHAR(255) */
    private String classPlace;

    /** Column: beginningDate DATE(10) */
    @Nullable
    private Date beginningDate;

    /** Column: completedDate DATE(10) */
    @Nullable
    private Date completedDate;

    /** Column: examPlace NVARCHAR(255) */
    private String examPlace;

    /** Column: examDate DATE(10) */
    @Nullable
    private Date examDate;

    /** Column: firstClassDate DATE(10) */
    @Nullable
    private Date firstClassDate;

    /** Column: classNote NVARCHAR(255) */
    private String classNote;

    /** Column: recruiterType TINYINT(3) */
    private ValueAutocompleteDto recruiterType;

    /** Column: recruiterCode BIGINT(19) */
    private String recruiterCode;

    /** Column: recruiterIdNo VARCHAR(255) */
    private String recruiterIdNo;

    /** Column: recruiterName NVARCHAR(255) */
    private String recruiterName;

    /** Column: recruiterAdCode BIGINT(19) */
    private Long recruiterAdCode;

    /** Column: recruiterAdName NVARCHAR(255) */
    private String recruiterAdName;
    private String recruiterAdPosition;

    /** Column: recruiterPosition NVARCHAR(255) */
    private String recruiterPosition;

    /** Column: managerType TINYINT(3) */
    private ValueAutocompleteDto managerType;

    /** Column: managerCode BIGINT(19) */
    private String managerCode;

    /** Column: managerIdNo VARCHAR(255) */
    private String managerIdNo;

    /** Column: managerName NVARCHAR(255) */
    private String managerName;

    /** Column: managerAdCode BIGINT(19) */
    private Long managerAdCode;

    /** Column: managerAdName NVARCHAR(255) */
    private String managerAdName;
    private String managerAdPosition;

    /** Column: managerPosition NVARCHAR(255) */
    private String managerPosition;

    /** Column: managerIndirectType TINYINT(3) */
    private ValueAutocompleteDto managerIndirectType;

    /** Column: managerIndirectCode BIGINT(19) */
    private String managerIndirectCode;

    /** Column: managerIndirectIdNo VARCHAR(255) */
    private String managerIndirectIdNo;

    /** Column: managerIndirectName NVARCHAR(255) */
    private String managerIndirectName;

    /** Column: managerIndirectAdCode BIGINT(19) */
    private Long managerIndirectAdCode;

    /** Column: managerIndirectAdName NVARCHAR(255) */
    private String managerIndirectAdName;
    private String managerIndirectAdPosition;

    /** Column: managerIndirectPosition NVARCHAR(255) */
    private String managerIndirectPosition;

    /** Column: checkEkycStatus TINYINT(3) */
    private Boolean checkEkycStatus;

    /** Column: ekycOption TINYINT(3) */
    private String ekycOption;

    /** Column: ekycSendMailTime DATETIME(23) */
    @Nullable
    private Date ekycSendMailTime;

    /** Column: ekycResult TINYINT(3) */
    private String ekycResult;

    /** Column: ekycStatus VARCHAR(10) */
    private String ekycStatus;

    /** Column: checkInterviewStatus TINYINT(3) */
    private String checkInterviewStatus;

    /** Column: interviewSendMailTime DATETIME(23) */
    @Nullable
    private Date interviewSendMailTime;

    /** Column: interviewComment TINYINT(3) */
    private String interviewComment;
    
    private String interview2Comment;

    /** Column: interviewPoint VARCHAR(10) */
    private String interviewPoint;
    private String interview2Point;

    /** Column: interviewStatus VARCHAR(10) */
    private String interviewStatus;
    private String interview2Status;

    /** Column: avicadStatus VARCHAR(10) */
    private String avicadStatus;

    /** Column: checkAvicadStatus TINYINT(3) */
    private String checkAvicadStatus;

    /** Column: refAttachement VARCHAR(255) */
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
    @Nullable
    private Date createdDate;
    
    private String updatedBy;
    @Nullable
    private Date updatedDate;
    
    private List<ErsWorkingExperience> listWorkingExperience;
    
    private List<AdoFamilyInfor> listFamilyInfor;
    
    private List<ErsCandidateQA> surveyQuestion;
    
    private String idFile1;
    
    private String idFile2;
    
    /** Column: OTHER_POSITION  NVARCHAR(255) */
    private String otherPosition;
    
    private List<ErsCandidateAttachDocumentDto> fileAttachment;
    
    // day la danh sach cau hoi pv thay doi theo chuc vu, gom nhung cau hoi co id nho hon 100
    private List<ErsInterviewResult> interviewQuestion;
    
    // danh sach nay se khong thay doi theo chuc vu, gom nhung cau hoi co id tu 101 tro di 
    private List<ErsInterviewResult> interviewOverall;
    
    private String personalImage;
    
    private List<ErsCandidatePlan> plan12MonthAM;
    
    private List<ErsCandidatePlan> plan12MonthAD;
    private String formStatusCode;
    
    private String hasRelationInsu;
    
    private Integer interviewTimes;
    
    private String secretKey;
    
    private Date secretDate;
    
    private String ekycImage;
    
    private String ekycImageUrl;
    
    private Map<String, List<ErsInterviewResult>> interviewResultList;
    
    private List<Long> listId;
    
    @JsonIgnore
    private String otp;
    
    private String errorBusiness;
    private ValueAutocompleteDto manager;
    
    private Integer userInputFlag;
    
    private Date userSubmitDate;
    
    private String interviewImage1;
    
    private String interviewImage2;
    
    private Integer slaFlag;
    
}
