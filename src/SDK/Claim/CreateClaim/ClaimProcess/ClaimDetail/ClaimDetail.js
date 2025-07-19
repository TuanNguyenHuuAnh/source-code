// import 'antd/dist/antd.min.css';

import React, { Component } from 'react';
import moment from 'moment'
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, AUTHENTICATION, COMPANY_KEY, CLAIM_TYPE, CLAIM_STEPCODE, FE_BASE_URL, IS_MOBILE } from '../../../../sdkConstant';

import { CLAIM_STATE, INITIAL_STATE } from '../../CreateClaimSDK';
import TreatmentDetail from './TreatmentDetail';
import AccidentDetail from './AccidentDetail';
import DeathDetail from './DeathDetail';
import { getSession, isCheckedOnlyDeadth, haveCheckedDeadth, isCheckedOnlyHC_HS, validDeathDate, validAccidentDate, validSickDate, validFacilityDate, getDeviceId, getBenifits, getUrlParameter } from '../../../../sdkCommon';
import ConfirmPopup from '../../../../components/ConfirmPopup';
import LoadingIndicator from '../../../../LoadingIndicator2';

class ClaimDetail extends Component {

  constructor(props) {
    super(props);

    this.state = {
      jsonInput: {
        jsonDataInput: {
          Company: COMPANY_KEY,
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          APIToken: getSession(ACCESS_TOKEN),
          Project: 'mcp',
          UserID: getSession(USER_LOGIN),
          ClientID: getSession(CLIENT_ID),
        }
      },

      stepName: this.props.handlerGetStep(CLAIM_STEPCODE.CLAIM_DETAIL),//CLAIM_STATE.CLAIM_DETAIL,

      claimDetailState: INITIAL_STATE().claimDetailState,

      showConfirmClear: false
    }

    this.handlerValidateAccidentDate = this.validateAccidentDate.bind(this);
    this.handlerUpdateSubClaimDetailState = this.updateSubClaimDetailState.bind(this);
    this.handlerTreatmentAt = this.TreatmentAt.bind(this);
    this.handlerNoTreatmentAt = this.NoTreatmentAt.bind(this);
    this.handlerSelectedHospitalChosen = this.SelectedHospitalChosenFacility.bind(this);
  }

  componentDidMount() {
    var jsonState = this.state;
    jsonState.claimDetailState = this.props.claimDetailState;
    this.setState(jsonState);
    document.getElementById('scrollAnchor').scrollIntoView({ behavior: 'smooth' });
  }

  validateAccidentDate() {
  }

  updateSubClaimDetailState(subStateName, editedState) {
    const claimTypeState = this.props.claimTypeState;
    const jsonState = { ...this.state };

    jsonState.claimDetailState[subStateName] = editedState;

    // Common function to calculate maximum date
    const calculateMaxDate = (dates) => {
      return dates.reduce((maxDate, date) => {
        return date && new Date(date) < maxDate ? new Date(date) : maxDate;
      }, new Date());
    };

    if (subStateName === "facilityList") {
      const facilityList = jsonState.claimDetailState.facilityList;

      if (claimTypeState.isAccidentClaim) {
        const maxAccidentDate = calculateMaxDate(facilityList.map((facility) => facility.startDate));
        const pAccidentInfo = jsonState.claimDetailState.accidentInfo;

        if (moment(pAccidentInfo.date, "yyyy-MM-DD") > moment(maxAccidentDate)) {
          pAccidentInfo.errors.dateValid = "Ngày xảy ra tai nạn không được lớn hơn ngày khám/điều trị tại cơ sở y tế";
        } else {
          pAccidentInfo.errors.dateValid = "";
        }
      }

      // ... (other code)

    } else if (subStateName === "accidentInfo") {
      if (claimTypeState.isDeathClaim) {
        // Common function to check date validity
        const checkDateValidity = (date, comparisonDate, errorMessage) => {
          if (date && new Date(date) < comparisonDate) {
            pDeathInfo.errors.dateValid = errorMessage;
          }
        };

        const pDeathInfo = jsonState.claimDetailState.deathInfo;
        const maxDeathDate = calculateMaxDate([
          jsonState.claimDetailState.sickInfo.sickFoundTime,
          jsonState.claimDetailState.facilityList.map((facility) => facility.startDate),
          jsonState.claimDetailState.accidentInfo.date
        ]);

        const isInit = maxDeathDate === new Date();

        checkDateValidity(pDeathInfo.date, maxDeathDate, "Ngày tử vong không được trước thời điểm khởi phát bệnh.");
        checkDateValidity(pDeathInfo.date, maxDeathDate, "Ngày tử vong không được trước ngày xảy ra tai nạn.");
        checkDateValidity(pDeathInfo.date, maxDeathDate, "Ngày tử vong không được trước ngày khám/điều trị tại cơ sở y tế");
      }

      // ... (other code)
    }

    this.setState(jsonState);
    this.props.handlerUpdateMainState("claimDetailState", jsonState.claimDetailState);
  }


  SelectedHospitalChosenFacility(index, value) {
    let jsonState = this.state;
    jsonState.claimDetailState.disabledButton = !this.isEnableButton(jsonState.claimDetailState);
    this.setState(jsonState);
    this.props.SelectedHospitalChosen(index, value);
  }

  isEnableButton(claimDetailState) {
    let errors, sickInfoOthers, accidentInfoOthers, deathInfoOthers;
    // Thông tin bệnh
    ({ errors, ...sickInfoOthers } = claimDetailState.sickInfo);
    if (!this.props.claimTypeState.isAccidentClaim
      && Object.entries(sickInfoOthers)
        .filter(([_, v]) => (v && (((typeof v === "string") && (v.trim().length > 0)) || (v instanceof Array && v.length > 0) || (v instanceof moment)))).length > 0) {
      return true;
    }
    // if (claimDetailState.isTreatmentAt !== null) {
    //   return true;
    // }
    // Thông tin khám/điều trị tại Cơ sở y tế
    for (const facility of claimDetailState.facilityList) {
      let { errors, endDate, ...commonFacilityInfo } = facility;
      // console.log(commonFacilityInfo);
      // if (Object.entries(commonFacilityInfo)
      //   .filter(([k, v]) => (
      //     (k !== 'cityCode' && k !== 'cityName' && k !== 'districtCode' && k !== 'districtName') && (v === null || v === undefined || !v)
      //   )).length > 0) {
      //   return false;
      // }
      if (claimDetailState.isTreatmentAt === true) {
        if (facility.treatmentType || ((typeof facility.selectedHospital === "string") && facility.selectedHospital.trim().length > 0) || (typeof facility.diagnosis === "string" && facility.diagnosis.trim().length > 0) || facility.diagnosis.length > 0) {
          return true;
        }
        if ((facility.treatmentType === "IN") && endDate) {
          return true;
        }
        if (facility.invoiceList) {
          for (const invoice of facility.invoiceList) {
            if ((typeof invoice.invoiceNumber === "string") && (invoice.invoiceNumber.trim().length > 0) || invoice.invoiceAmount) {
              return true;
            }
          }
        }
        // Công ty bảo hiểm khác
        if (facility.isOtherCompanyRelated === 'yes' && (((facility.otherCompanyInfo.companyName !== null) && (facility.otherCompanyInfo.companyName !== undefined) && (facility.otherCompanyInfo.companyName.trim().length > 0)) || (facility.otherCompanyInfo.paidAmount))) {
          return true;
        }
        if (facility.isOtherCompanyRelated === 'no') {
          return true;
        }
      }

    }
    // Tai nạn
    ({ errors, ...accidentInfoOthers } = claimDetailState.accidentInfo);
    if (this.props.claimTypeState.isAccidentClaim) {
      // if (accidentInfoOthers.hour instanceof moment || accidentInfoOthers.date instanceof moment) {
      //   return true;
      // }
      if (Object.entries(accidentInfoOthers)
        .filter(([k, v]) => (
          (v && (((typeof v === "string") && (v.trim().length > 0)) || ((typeof v !== "string") && v.length > 0) || (v instanceof Array && v.length > 0) || (v instanceof moment)))
        )).length > 0) {
        return true;
      }
    }
    // Tử vong
    if (this.props.claimCheckedMap[CLAIM_TYPE.DEATH]) {
      ({ errors, ...deathInfoOthers } = claimDetailState.deathInfo);
      if (Object.entries(deathInfoOthers)
        .filter(([k, v]) => (v && (((typeof v === "string") && (v.trim().length > 0)) || (v instanceof Array && v.length > 0) || (v instanceof moment)))).length > 0) {
        return true;
      }
    }
    return false;
  }

  isValidClaimDetailState(claimDetailState) {
    this.addErrors(claimDetailState);
    let errors, sickInfoOthers, accidentInfoOthers, deathInfoOthers;
    // Thông tin bệnh
    ({ errors, ...sickInfoOthers } = claimDetailState.sickInfo);
    if (!this.props.claimTypeState.isAccidentClaim) {
      if (claimDetailState.sickInfo.errors.dateValid) {
        return false;
      }
      if (this.props.claimDetailState.isTreatmentAt && Object.entries(sickInfoOthers)
        .filter(([k, v]) => (k !== '' && k !== 'sickFoundFacilityOther' && k !== 'sickFoundFacilityChosen') && (v === null || v === undefined || !v || ((typeof v === "string") && (v.trim().length === 0)))).length > 0) {
        return false;
      }
    } else {
      if (isCheckedOnlyDeadth(this.props.claimCheckedMap) && Object.entries(claimDetailState.accidentInfo)
        .filter(([k, v]) => (k !== 'healthStatus' && k !== 'selectedNation') && (v === null || v === undefined || !v || ((typeof v === "string") && (v.trim().length === 0)))).length > 0) {
        return false;
      } else if (!haveCheckedDeadth(this.props.claimCheckedMap) && !isCheckedOnlyHC_HS(this.props.claimCheckedMap)) {
        if (this.props.isVietnam) {
          if (Object.entries(claimDetailState.accidentInfo)
          .filter(([k, v]) => (k !== 'selectedNation') && (v === null || v === undefined || !v || ((typeof v === "string") && (v.trim().length === 0)))).length > 0) {
            return false;
          }
        } else if ((this.props.isVietnam !== null) && (this.props.isVietnam === false)) {
          if (Object.entries(claimDetailState.accidentInfo)
          .filter(([k, v]) => ((k !== 'selectedNation') && (k !== 'cityCode') && (k !== 'cityName') && (k !== 'districtCode') && (k != 'districtName') ) && (v === null || v === undefined || !v || ((typeof v === "string") && (v.trim().length === 0)))).length > 0) {
            return false;
          }
        }

      }
    }
    if (this.props.claimDetailState.isTreatmentAt === null) {
      return false;
    }
    // Thông tin khám/điều trị tại Cơ sở y tế
    for (const facility of claimDetailState.facilityList) {
      let { errors, endDate, ...commonFacilityInfo } = facility;
      // console.log(commonFacilityInfo);
      // if (Object.entries(commonFacilityInfo)
      //   .filter(([k, v]) => (
      //     (k !== 'cityCode' && k !== 'cityName' && k !== 'districtCode' && k !== 'districtName') && (v === null || v === undefined || !v)
      //   )).length > 0) {
      //   return false;
      // }
      if (this.props.claimDetailState.isTreatmentAt === true) {
        if (facility.errors.dateValid) {
          return false;
        }
        let listEmpty = Object.entries(commonFacilityInfo).filter(([k, v]) => (
          (k !== '' && k !== 'name' && k !== 'otherDiagnostic' && k !== 'otherHospital' && k !== 'cityCode' && k !== 'districtCode' && k !== 'districtName' && k !== 'selectedHospitalChosen') && (v === null || v === undefined || !v || ((typeof v === "string") && (v.trim().length === 0)))
        ));
        if (listEmpty.length > 0) {
          return false;
        }
        if (!facility.treatmentType || !facility.selectedHospital || !facility.diagnosis || (facility.diagnosis.length === 0)) {
          return false;
        }
        if ((facility.treatmentType === "IN") && (endDate === null || endDate === undefined || !endDate)) {
          return false;
        }
        if (this.props.claimCheckedMap[CLAIM_TYPE.HEALTH_CARE]) {
          if (facility.invoiceList) {
            for (const invoice of facility.invoiceList) {
              if (!invoice.invoiceNumber || (invoice.invoiceNumber.trim().length === 0) || !invoice.invoiceAmount) {
                return false;
              }
            }
          }
        }
        if (!facility.isOtherCompanyRelated) {
          return false;
        }
        if (facility.isOtherCompanyRelated === 'yes' && (!facility.otherCompanyInfo.companyName || (facility.otherCompanyInfo.companyName.trim().length === 0) || !facility.otherCompanyInfo.paidAmount)) {
          return false;
        }
      }
    }
    if (claimDetailState.accidentInfo.errors.dateValid) {
      return false;
    }
    // Tai nạn
    ({ errors, ...accidentInfoOthers } = claimDetailState.accidentInfo);
    if (this.props.claimTypeState.isAccidentClaim) {
      if (this.props.claimCheckedMap[CLAIM_TYPE.DEATH] || this.props.claimCheckedMap[CLAIM_TYPE.HEALTH_CARE] || this.props.claimCheckedMap[CLAIM_TYPE.HS]) {
        if (Object.entries(accidentInfoOthers)
          .filter(([k, v]) => (
            (k !== 'cityCode' && k !== 'cityName' && k !== 'districtCode' && k !== 'districtName' && k !== 'selectedNation' && k != 'healthStatus') && (v === null || v === undefined || !v || ((typeof v === "string") && (v.trim().length === 0)))
          )).length > 0) {
          return false;
        }
      } else {
        if (Object.entries(accidentInfoOthers)
          .filter(([k, v]) => (
            (k !== 'cityCode' && k !== 'cityName' && k !== 'districtCode' && k !== 'districtName' && k !== 'selectedNation') && (v === null || v === undefined || !v || ((typeof v === "string") && (v.trim().length === 0)))
          )).length > 0) {
          return false;
        }
      }

    }
    // Tử vong
    if (this.props.claimCheckedMap[CLAIM_TYPE.DEATH]) {
      if (claimDetailState.deathInfo.errors.dateValid) {
        return false;
      }
      ({ errors, ...deathInfoOthers } = claimDetailState.deathInfo);
      if (Object.entries(deathInfoOthers)
        .filter(([k, v]) => (
          (k !== 'cityCode' && k !== 'cityName' && k !== 'districtCode' && k !== 'districtName') && (v === null || v === undefined || !v || ((typeof v === "string") && (v.trim().length === 0)))
        )).length > 0) {
        return false;
      }
    }
    return true;
  }

  addErrors(claimDetailState) {
    let errors, sickInfoOthers, accidentInfoOthers, deathInfoOthers;
    // Thông tin bệnh
    ({ errors, ...sickInfoOthers } = claimDetailState.sickInfo);
    claimDetailState.sickInfo.errors.errorList = [];
    // claimDetailState.accidentInfo.errors.errorList = [];

    if (!this.props.claimTypeState.isAccidentClaim && this.props.claimDetailState.isTreatmentAt) {
      let listEmpty = Object.entries(claimDetailState.sickInfo).filter(([k, v]) => (v === null || v === undefined || !v || ((typeof v === "string") && (v.trim().length === 0))));
      let list = Object.values(listEmpty);
      for (let i = 0; i < list.length; i++) {
        claimDetailState.sickInfo.errors.errorList.push(list[i][0]);
      }
      claimDetailState.sickInfo = validSickDate(claimDetailState.sickInfo.sickFoundTime, claimDetailState.sickInfo, claimDetailState.facilityList, claimDetailState.deathInfo);
    }

    if (claimDetailState.isTreatmentAt === null) {
      claimDetailState.errorMsg = 'Vui lòng chọn có được khám/ điều trị tại cơ sở y tế.';
    } else {
      claimDetailState.errorMsg = '';
    }
    // Thông tin khám/điều trị tại Cơ sở y tế
    for (let facility of claimDetailState.facilityList) {
      // let facility = claimDetailState.facilityList[i];
      let { errors, ...commonFacilityInfo } = facility;
      // console.log(commonFacilityInfo);
      // if (Object.entries(commonFacilityInfo)
      //   .filter(([k, v]) => (
      //     (k !== 'cityCode' && k !== 'cityName' && k !== 'districtCode' && k !== 'districtName') && (v === null || v === undefined || !v)
      //   )).length > 0) {
      //   return false;
      // }
      facility.errors.errorList = [];
      if (this.props.claimDetailState.isTreatmentAt === true) {
        let listEmpty = Object.entries(commonFacilityInfo).filter(([k, v]) => (
          (v === null || v === undefined || !v || ((typeof v === "string") && (v.trim().length === 0)))
        ));
        let list = Object.values(listEmpty);
        for (let i = 0; i < list.length; i++) {
          facility.errors.errorList.push(list[i][0]);
        }

        if (!facility.treatmentType) {
          facility.errors.errorList.push('treatmentType');
        }
        if (!facility.diagnosis || (facility.diagnosis.length === 0)) {
          facility.errors.errorList.push('diagnosis');
        }
        // if (!facility.treatmentType || !facility.selectedHospital || !facility.diagnosis) {
        //   return false;
        // }
        // if (!facility.treatmentType || !facility.selectedHospital || !facility.diagnosis) {
        //   return false;
        // }
        // if ((facility.treatmentType === "IN") && (endDate === null || endDate === undefined || !endDate)) {
        //   return false;
        // }
        if (this.props.claimCheckedMap[CLAIM_TYPE.HEALTH_CARE]) {
          if (facility.invoiceList) {
            for (const invoice of facility.invoiceList) {
              invoice.errorList = [];
              let listEmpty = Object.entries(invoice).filter(([k, v]) => (
                (v === null || v === undefined || !v || ((typeof v === "string") && (v.trim().length === 0)))
              ));
              let list = Object.values(listEmpty);
              for (let j = 0; j < list.length; j++) {
                invoice.errorList.push(list[j][0]);
              }

            }
          }
        }
        if (!facility.isOtherCompanyRelated) {
          facility.errors.errorList.push('isOtherCompanyRelated');
        } else if (facility.isOtherCompanyRelated === 'yes') {
          if (!facility.otherCompanyInfo.companyName || (facility.otherCompanyInfo.companyName.trim().length === 0)) {
            facility.errors.errorList.push('companyName');
          }
          if (!facility.otherCompanyInfo.paidAmount) {
            facility.errors.errorList.push('paidAmount');
          }
        }
      }
      facility = validFacilityDate(facility, facility.startDate, claimDetailState.sickInfo, claimDetailState.accidentInfo, claimDetailState.deathInfo);
      if (facility.treatmentType === 'IN') {
        facility = validFacilityDate(facility, facility.endDate, claimDetailState.sickInfo, claimDetailState.accidentInfo, claimDetailState.deathInfo);
      }
      

    }
    // Tai nạn
    ({ errors, ...accidentInfoOthers } = claimDetailState.accidentInfo);
    claimDetailState.accidentInfo.errors.errorList = [];
    if (this.props.claimTypeState.isAccidentClaim) {
      let listEmpty = [];
      if (!haveCheckedDeadth(this.props.claimCheckedMap) && !isCheckedOnlyHC_HS(this.props.claimCheckedMap)) {
        if (this.props.isVietnam) {
          listEmpty = Object.entries(accidentInfoOthers)
            .filter(([k, v]) => (
              (k !== 'selectedNation') && (v === null || v === undefined || !v || ((typeof v === "string") && (v.trim().length === 0)))
            ));
        } else {
          listEmpty = Object.entries(accidentInfoOthers)
            .filter(([k, v]) => (
              (k !== 'cityCode' && k !== 'cityName' && k !== 'districtCode' && k !== 'districtName') && (v === null || v === undefined || !v || ((typeof v === "string") && (v.trim().length === 0)))
            ));
        }
      }
      else {
        if (this.props.isVietnam) {
          listEmpty = Object.entries(accidentInfoOthers)
            .filter(([k, v]) => (
              (k !== 'selectedNation') && (k != 'healthStatus') && (v === null || v === undefined || !v || ((typeof v === "string") && (v.trim().length === 0)))
            ));
        } else {
          listEmpty = Object.entries(accidentInfoOthers)
            .filter(([k, v]) => (
              (k !== 'cityCode' && k !== 'cityName' && k !== 'districtCode' && k !== 'districtName' && k != 'healthStatus') && (v === null || v === undefined || !v || ((typeof v === "string") && (v.trim().length === 0)))
            ));
        }
      }


      let list = Object.values(listEmpty);
      for (let i = 0; i < list.length; i++) {
        claimDetailState.accidentInfo.errors.errorList.push(list[i][0]);
      }
      claimDetailState.accidentInfo = validAccidentDate(claimDetailState.accidentInfo.date, claimDetailState.accidentInfo, claimDetailState.facilityList, claimDetailState.deathInfo);
    }
    // Tử vong
    claimDetailState.deathInfo.errors.errorList = [];
    if (this.props.claimCheckedMap[CLAIM_TYPE.DEATH]) {
      ({ errors, ...deathInfoOthers } = claimDetailState.deathInfo);
      let listEmpty = Object.entries(deathInfoOthers)
        .filter(([k, v]) => (
          (v === null || v === undefined || !v || ((typeof v === "string") && (v.trim().length === 0)))
        ));
      let list = Object.values(listEmpty);
      for (let i = 0; i < list.length; i++) {
        claimDetailState.deathInfo.errors.errorList.push(list[i][0]);
      }
      claimDetailState.deathInfo = validDeathDate(claimDetailState.deathInfo.date, claimDetailState.deathInfo, claimDetailState.sickInfo, claimDetailState.accidentInfo, claimDetailState.facilityList);
    }

    // Công ty bảo hiểm khác
    claimDetailState.errorList = [];
    // if (claimDetailState.isTreatmentAt) {
    //   if (claimDetailState.isOtherCompanyRelated !== 'yes'
    //     && claimDetailState.isOtherCompanyRelated !== 'no') {
    //     claimDetailState.errorList.push('isOtherCompanyRelated');
    //   }
    //   if (claimDetailState.isOtherCompanyRelated === 'yes') {
    //     //for (const otherCompany of claimDetailState.otherCompanyInfoList) {
    //     if (claimDetailState.otherCompanyInfo.companyName === null || claimDetailState.otherCompanyInfo.companyName === undefined || claimDetailState.otherCompanyInfo.companyName === '') {
    //       claimDetailState.errorList.push('companyName');
    //     }
    //     //}
    //   }
    // }
    this.focusErrorField(claimDetailState);
    this.props.handlerUpdateMainState("claimDetailState", claimDetailState);
  }

  focusErrorField(claimDetailState) {
    //focus death
    const deathInfo = claimDetailState.deathInfo;
    if (deathInfo.errors.dateValid) {
      if (document.getElementById('death_date')) {
        document.getElementById('death_date').focus();
        return;
      }
    }
    if (deathInfo.errors.errorList.length > 0) {
      if (document.getElementById('death_' + deathInfo.errors.errorList[0])) {
        document.getElementById('death_' + deathInfo.errors.errorList[0]).focus();
        return;
      }
    }
    //focus sick
    const sickInfo = claimDetailState.sickInfo;
    if (sickInfo.errors.dateValid) {
      if (document.getElementById('sick_sickFoundTime')) {
        document.getElementById('sick_sickFoundTime').focus();
        return;
      }
    }
    if (sickInfo.errors.errorList.length > 0) {
      if (document.getElementById('sick_' + sickInfo.errors.errorList[0])) {
        document.getElementById('sick_' + sickInfo.errors.errorList[0]).focus();
        return;
      }
    }
    //focus accident
    const accidentInfo = claimDetailState.accidentInfo;
    if (accidentInfo.errors.dateValid) {
      if (document.getElementById('accident_date')) {
        document.getElementById('accident_date').focus();
        return;
      }
    }
    if (accidentInfo.errors.errorList.length > 0) {
      if (document.getElementById('accident_' + accidentInfo.errors.errorList[0])) {
        document.getElementById('accident_' + accidentInfo.errors.errorList[0]).focus();
        return;
      }
    }
    //focus facility
    const facilityList = claimDetailState.facilityList;
    for (let i = 0; i < facilityList.length; i++) {
      let fDetail = facilityList[i];
      if (fDetail.errors.dateValid) {
        if (document.getElementById('facility_startDate_' + i)) {
          document.getElementById('facility_startDate_' + i).focus();
          return;
        }
      }
      if (fDetail.errors.errorList.length > 0) {
        for (let j = 0; j < fDetail.errors.errorList.length; j++) {
          let error = fDetail.errors.errorList[j];
          if (document.getElementById('facility_' + error + '_' + i)) {
            document.getElementById('facility_' + error + '_' + i).focus();
            return;
          }
        }
        if (fDetail.invoiceList.length > 0) {
          for (let k = 0; k < fDetail.invoiceList.length; k++) {
            let invoice = fDetail.invoiceList[k];
            if (document.getElementById('facility_' + i + '_' + invoice.errorList[0] + '_' + k)) {
              document.getElementById('facility_' + i + '_' + invoice.errorList[0] + '_' + k).focus();
              return;
            }

          }
        }



      }
    }

  }

  TreatmentAt() {
    let jsonState = this.state;
    jsonState.claimDetailState.isTreatmentAt = true;
    jsonState.claimDetailState.disabledButton = !this.isEnableButton(jsonState.claimDetailState);
    this.setState(jsonState);
    // this.props.HaveTreatmentAt();
  }

  NoTreatmentAt() {
    let jsonState = this.state;
    jsonState.claimDetailState.isTreatmentAt = false;
    jsonState.claimDetailState.disabledButton = !this.isEnableButton(jsonState.claimDetailState);
    this.setState(jsonState);
    // this.props.NoHaveTreatmentAt();
  }

  render() {
    const showConfirmClear = () => {
      if (this.props.disableEdit && !this.props.agentKeyInPOSelfEdit) {
        this.props.handlerBackToPrevStep(this.props.handlerGetStep(CLAIM_STEPCODE.CLAIM_DETAIL));
      } else {
        this.setState({ showConfirmClear: true });
      }
    }
    const closeShowConfirmClear = () => {
      this.setState({ showConfirmClear: false });
    }
    const agreeClear = () => {
      this.props.handlerClearBelowBenifit();
      // this.props.handlerBackToPrevStep(this.state.stepName);
      this.props.handlerBackToPrevStep(this.props.handlerGetStep(CLAIM_STEPCODE.CLAIM_DETAIL));
    }

    const validateAndSubmit = () => {
      if (!this.isValidClaimDetailState(this.state.claimDetailState)) {
        return;
      }
      this.props.handlerSubmitClaimDetail();
    }
    const goBack = () => {
      const main = document.getElementById("main-id");
      if (main) {
        main.classList.toggle("nodata");
      }
    }
    const claimDetailState = this.state.claimDetailState;
    const claimTypeState = this.props.claimTypeState;
    const claimCheckedMap = this.props.claimCheckedMap;
    return (
      <section className="sccontract-warpper" id="scrollAnchor">
        <div className="insurance">
          <div className={getSession(IS_MOBILE)?"heading padding-heading-mobile": "heading"}>
            {(this.props.sourceSystem === 'DConnect') &&
            !getSession(IS_MOBILE) &&
            <div className="breadcrums">
              <div className="breadcrums__item">
                <p>Yêu cầu quyền lợi</p>
                <span>&gt;</span>
              </div>
              <div className="breadcrums__item">
                <p>Tạo mới yêu cầu</p>
                <span>&gt;</span>
              </div>
            </div>
            }
            {!getSession(IS_MOBILE) &&
            <div className="other_option" id="other-option-toggle" onClick={()=>goBack()}>
              <p>Chọn thông tin</p>
              <i><img src={FE_BASE_URL + "/img/icon/return_option.svg"} alt="" /></i>
            </div>
            }
            <div className={getSession(IS_MOBILE) ? "heading__tab mobile" : "heading__tab"}>
              <div className="step-container">
                <div className="step-wrapper">
                  <div className="step-btn-wrapper">
                    <div className="back-btn" >
                      <button>
                        <div className="svg-wrapper" onClick={() => showConfirmClear()}>
                          <svg
                            width="11"
                            height="15"
                            viewBox="0 0 11 15"
                            fill="none"
                            xmlns="http://www.w3.org/2000/svg"
                          >
                            <path
                              d="M9.31149 14.0086C9.13651 14.011 8.96586 13.9566 8.82712 13.8541L1.29402 8.1712C1.20363 8.10293 1.13031 8.01604 1.07943 7.91689C1.02856 7.81774 1.00144 7.70887 1.00005 7.59827C0.998661 7.48766 1.02305 7.37814 1.07141 7.27775C1.11978 7.17736 1.1909 7.08865 1.27955 7.01814L8.63636 1.17893C8.71445 1.1171 8.80442 1.07066 8.90112 1.04227C8.99783 1.01387 9.09938 1.00408 9.19998 1.01344C9.40316 1.03235 9.59013 1.12816 9.71976 1.27978C9.84939 1.4314 9.91106 1.62642 9.89121 1.82193C9.87135 2.01745 9.7716 2.19744 9.6139 2.32231L2.99589 7.57403L9.77733 12.6865C9.90265 12.7809 9.99438 12.9104 10.0398 13.0572C10.0853 13.204 10.0823 13.3608 10.0311 13.506C9.97999 13.6511 9.88328 13.7774 9.75437 13.8675C9.62546 13.9575 9.4707 14.0068 9.31149 14.0086Z"
                              fill="#985801"
                              stroke="#985801"
                              strokeWidth="0.2"
                            />
                          </svg>
                        </div>
                        {!getSession(IS_MOBILE) ? (
                          <span className="simple-brown" onClick={() => showConfirmClear()}>Quay lại</span>
                        ) : (
                          <p style={{ textAlign: 'center', paddingLeft: '16px', minWidth: '250%', fontWeight: '700' }}>{'Tạo mới yêu cầu'}</p>
                        )}
                      </button>
                      
                    </div>
                    {getSession(IS_MOBILE) &&
                      <div className="step-btn-save-quit">
                        <div className='save-quit-wrapper'>
                          <button>
                            <span className="simple-brown" onClick={this.props.handleSaveLocalAndQuit}>Lưu & thoát</span>
                          </button>
                        </div>
                      </div>
                    }
                    {/* <div className="save-wrap">
                  <button className="back-text">Lưu</button>
                </div> */}
                  </div>
                  <div className="progress-bar">
                    <div className={(this.state.stepName === this.props.handlerGetStep(CLAIM_STEPCODE.CLAIM_TYPE) || this.state.stepName === this.props.handlerGetStep(CLAIM_STEPCODE.CLAIM_DETAIL)) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>1</span>
                      </div>
                      <p>Thông tin sự kiện</p>
                    </div>
                    <div className={(this.state.stepName === this.props.handlerGetStep(CLAIM_STEPCODE.PAYMENT_METHOD)) || (this.state.stepName === this.props.handlerGetStep(CLAIM_STEPCODE.CONTACT)) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>2</span>
                      </div>
                      <p>Thanh toán <br />và liên hệ</p>
                    </div>
                    <div className={(this.state.stepName === this.props.handlerGetStep(CLAIM_STEPCODE.ATTACHMENT)) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>3</span>
                      </div>
                      <p>Kèm <br />chứng từ</p>
                    </div>
                    <div className={(this.state.stepName === this.props.handlerGetStep(CLAIM_STEPCODE.SUBMIT)) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>4</span>
                      </div>
                      <p>Hoàn tất yêu cầu</p>
                    </div>
                  </div>
                  {!getSession(IS_MOBILE) &&
                    <div className="step-btn-save-quit">
                      <div className='save-quit-wrapper'>
                        <button>
                          <span className="simple-brown" onClick={this.props.handleSaveLocalAndQuit}>Lưu & thoát</span>
                        </button>
                      </div>
                    </div>
                  }
                </div>
                
              </div>
            </div>
          </div>
          {getSession(IS_MOBILE) &&
            <div className='padding-top175 margin-top112'></div>
          }
          {this.props.systemGroupPermission?.[0]?.Role === 'AGENT' &&
            <div className='ndbh-info'>
              <div style={{ display: 'flex' }}>
                <p>Tên NĐBH:</p>
                <p className='bold-text'>{this.props.selectedCliInfo?.fullName}</p>
              </div>
              <div style={{ display: 'flex' }}>
                <p>Quyền lợi:</p>
                <p className='bold-text'>{getBenifits(claimCheckedMap)}</p>
              </div>
            </div>
          }
          <div className="stepform" id="scrollAnchor">
            <div className="stepform__body" style={{ paddingBottom: '0', borderBottomColor: '#ffffff', borderBottom: '0px', boxShadow: 'none' }}>
              <LoadingIndicator area="submit-init-claim"/>
              {(claimCheckedMap[CLAIM_TYPE.DEATH]) &&
                <DeathDetail
                  zipCodeList={this.props.zipCodeList}
                  claimTypeState={claimTypeState}
                  claimDetailState={claimDetailState}
                  sickInfo={claimDetailState.sickInfo}
                  facilityList={claimDetailState.facilityList}
                  accidentInfo={claimDetailState.accidentInfo}
                  deathInfo={claimDetailState.deathInfo}
                  selectedCliInfo={this.state.selectedCliInfo}
                  claimCheckedMap={claimCheckedMap}
                  disableEdit={this.props.disableEdit}
                  agentKeyInPOSelfEdit={this.props.agentKeyInPOSelfEdit}
                  handlerUpdateSubClaimDetailState={this.handlerUpdateSubClaimDetailState} />
              }
              {(claimTypeState.isAccidentClaim) &&
                <AccidentDetail
                  zipCodeList={this.props.zipCodeList}
                  nationList={this.props.nationList}
                  facilityList={claimDetailState.facilityList}
                  accidentInfo={claimDetailState.accidentInfo}
                  deathInfo={claimDetailState.deathInfo}
                  claimCheckedMap={claimCheckedMap}
                  disableEdit={this.props.disableEdit}
                  agentKeyInPOSelfEdit={this.props.agentKeyInPOSelfEdit}
                  handlerUpdateSubClaimDetailState={this.handlerUpdateSubClaimDetailState} inVietNam={this.props.inVietNam} notInVietNam={this.props.notInVietNam} isVietnam={this.props.isVietnam} />
              }
              {/* {component Benh} */}
              <TreatmentDetail
                zipCodeList={this.props.zipCodeList}
                hospitalList={this.props.hospitalList}
                hospitalResultList={this.props.hospitalResultList}
                isSickClaim={!claimTypeState.isAccidentClaim}
                sickInfo={claimDetailState.sickInfo}
                facilityList={claimDetailState.facilityList}
                // isOtherCompanyRelated={claimDetailState.isOtherCompanyRelated}
                otherCompanyInfoList={claimDetailState.otherCompanyInfoList}
                otherCompanyInfo={claimDetailState.otherCompanyInfo}
                claimDetailState={claimDetailState}
                selectedHospital={claimDetailState.facilityList.selectedHospital}
                diagnosis={claimDetailState.facilityList.diagnosis}
                claimCheckedMap={claimCheckedMap}
                HaveTreatmentAt={this.handlerTreatmentAt}
                NoHaveTreatmentAt={this.handlerNoTreatmentAt}
                SelectedHospital={this.props.SelectedHospital}
                SelectedHospitalChosen={this.handlerSelectedHospitalChosen}
                SelectedDiagnosticResult={this.props.SelectedDiagnosticResult}
                SelectOtherHospital={this.props.SelectOtherHospital}
                SelectOtherDiagnostic={this.props.SelectOtherDiagnostic}
                SelectedSickInfoPlaceChosen={this.props.SelectedSickInfoPlaceChosen}
                SelectOtherSickInfoPlace={this.props.SelectOtherSickInfoPlace}
                handlerValidateAccidentDate={this.handlerValidateAccidentDate}
                enterOtherCompanyName={this.props.enterOtherCompanyName}
                enterOtherCompanyPaid={this.props.enterOtherCompanyPaid}
                isOtherCompanyRelated={this.props.isOtherCompanyRelated}
                toggleICD={this.props.toggleICD}
                disableEdit={this.props.disableEdit}
                agentKeyInPOSelfEdit={this.props.agentKeyInPOSelfEdit}
                handlerUpdateSubClaimDetailState={this.handlerUpdateSubClaimDetailState} />
              {/* {component Tai nan} */}
              {/* {component Tu vong} */}

              <img className="decor-clip" src={FE_BASE_URL + "/img/mock.svg"} alt="" />
              <img className="decor-person" src={FE_BASE_URL + "/img/person.png"} alt="" />
            </div>
          </div>
          {getSession(IS_MOBILE)&&
          <div className='nd13-padding-bottom64'></div>
          }
          <div className="bottom-btn">
            <button className={(claimDetailState.disabledButton) ? "btn btn-primary disabled" : "btn btn-primary"}
              id="submitClaimDetail" disabled={!!(claimDetailState.disabledButton)} onClick={() => validateAndSubmit()}>{this.props.pinStep?'Lưu thông tin': 'Tiếp tục'}</button>
          </div>
        </div>
        {this.state.showConfirmClear &&
          <ConfirmPopup closePopup={() => closeShowConfirmClear()} go={() => agreeClear()} />
        }
      </section>
    );
  }
}

export default ClaimDetail;
