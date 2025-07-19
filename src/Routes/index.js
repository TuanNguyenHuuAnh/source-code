import React from 'react';
import {Redirect, Route, Switch} from 'react-router-dom';
import LifeAssured from "../PolInfo/LifeAssured";
import PolicyOwner from "../PolInfo/PolicyOwner";
import PolCompanyList from "../PolInfo/PolCompanyList";
import PolCompanyInvoiceList from "../PolInfo/PolCompanyInvoiceList";
import CreateClaim from "../Claim/CreateClaim/CreateClaim";
import AdditionalClaim from "../Claim/InfoRequiredClaim/AdditionalClaim";
import PaymentHistory from "../History/PaymentHistory";
import ExchangePoint from "../point/ExchangePoint";
import SpecialProgramPoint from "../point/SpecialProgramPoint";
import NormalProgramPoint from "../point/NormalProgramPoint";
import NotFound from "../healthWellbeing/NotFound";
import GiftCart from "../point/GiftCart";
import HistoryPoint from "../point/HistoryPoint";
import ExchangePointGeneric from "../point/ExchangePointGeneric";
import ClaimHistory from "../History/ClaimHistory";
import UpdateContactInfo from "../Update/UpdateContactInfo";
import NotificationPage from "../Notification/NotificationPage";
import UpdatePolicyInfo from "../Update/UpdatePolicyInfo";
import Reinstatement from "../Update/Reinstatement.js";
import PaymentContract from "../Update/PaymentContract";
import MyPayment from "../Payment/MyPayment";
import FamilyPayment from "../Payment/FamilyPayment";
import ClaimInfo from "../Followup/ClaimInfo";
import HealthNews from "../health/HealthNews";
import HealthNewsDetail from "../health/HealthNewsDetail";
import Maintainence from "../shared/Maintainence";
import ExchangePointSpecialNew from "../point/ExchangePointSpecialNew";
import AccountInfo from "../user/AccountInfo";
import EDocumentEpolicy from "../eDocument/EDocumentEpolicy.js";
import EDocumentInvoice from "../eDocument/EDocumentInvoice";
import ComEDocumentInvoice from "../eDocument/ComEDocumentInvoice";
import HCCard from "../user/HCCard";
import Utilities from "../utilities/Utilities";
import NetWork from "../utilities/NetWork";
import InsParticipation from "../utilities/InsParticipation";
import ClaimProcessGuide from "../utilities/ClaimProcessGuide";
import PolicyTrans from "../utilities/PolicyTrans";
import Clauses from "../utilities/Clauses";
import PolicyPayment from "../utilities/PolicyPayment";
import Faq from "../utilities/Faq";
import Document from "../utilities/Document";
import Occupation from "../utilities/Occupation";
import Contact from "../utilities/Contact";
import Feedback from "../utilities/Feedback";
import Terms from "../utilities/Terms";
import PrivacyPolicy from "../utilities/PrivacyPolicy";
import AdditionalClaimPublic from "../Claim/InfoRequiredClaim/AdditionalClaimPublic";
import EpolicyGeneral from "../eSingnature/EpolicyGeneral";
import CustomerCheck from "../customerCheck/CustomerCheck";
import VideoCustomer from "../customerCheck/VideoCustomer";
import SurveyForm from "../pages/surveyForm/SurveyForm";
import SearchArticle from "../healthWellbeing/SearchArticle";
import HealthLife from "../health/HealthLife";
import Tag from "../healthWellbeing/Tag";
import HealthLifeSecret from "../healthWellbeing/HealthLifeSecret";
import Category from "../healthWellbeing/Category";
import SubCategory from "../healthWellbeing/SubCategory";
import Article from "../healthWellbeing/Article";
import ChallengeDetail from "../healthWellbeing/ChallengeDetail";
import ChallengeList from "../healthWellbeing/ChallengeList";
import ComPolInfo from "../PolInfo/ComPolInfo";
import PolInfo from "../PolInfo/PolInfo";
import App from '../app/App';
import PasscodeCheck from "../Claim/ClaimND13/ND13Modal/ND13VerifyPasscode/ND13PasscodeCheck/PasscodeCheck";
import PasscodeCheckPayment from "../Claim/ClaimND13/ND13Modal/ND13VerifyPasscode/ND13PasscodeCheck/PasscodeCheckPayment";
import ND13 from "../SDK/ND13";
import ChangePayMode from "../SDK/ChangePayMode/ChangePayMode";
import UpdatePersonalInfo from "../SDK/PersonalInfo/UpdatePersonalInfo";
import CustomerNeedKnow from "../utilities/CustomerNeedKnow";
import DecreaseSACancelRider from "../SDK/DecreaseSACancelRider/DecreaseSACancelRider";
import ChangePayment from "../SDK/ChangePayment/ChangePayment";
import CreateClaimSDK  from   "../SDK/Claim/CreateClaim/CreateClaimSDK";
import ChangeSundryAmount from "../SDK/ChangeSundryAmount/ChangeSundryAmount";
import DConnectSDK from "../SDK/DConnectSDK.jsx";
import CreateRequest from "../SDK/Claim/CreateClaim/SDKCreateClaimRequest/CreateRequest.js";
import EClaimSDK from "../SDK/EClaimSDK.js";
import ErrorBoundary from "../SDK/ErrorBoundary.js";
import EDocumentViewer from "../SDK/EDocumentViewer.jsx"
import SDKReceiveLink from "../SDK/Claim/CreateClaim/SDKReceiveLink/SDKReceiveLink.js"
import CDYT from "../healthWellbeing/CDYT"
const Routes = () => {
    return (
        <ErrorBoundary>
        <Switch>
            <Route path="/mypolicyinfo/:id" component={PolInfo} />
            <Route path="/mypolicyinfo" component={PolInfo} />
            <Route path="/lifeassured/:id" component={LifeAssured} />
            <Route path="/lifeassured" component={LifeAssured} />
            <Route path="/policyowner" component={PolicyOwner} />
            <Route path="/companypolicyinfo" component={PolCompanyList} />
            <Route path="/compolicyinfo/:id" component={ComPolInfo} />
            <Route path="/companypolicyinvoiceinfo" component={PolCompanyInvoiceList} />
            <Route path="/myclaim1/:id/:index/:type" exact component={CreateClaim} />
            <Route path="/myclaim1/:id/:index" exact component={CreateClaim} />
            <Route path="/myclaim1/:info" exact component={CreateClaim} />
            <Route path="/myclaim1" component={CreateClaim} />
            {/* <Route path="/myclaim/:id/:index/:type" exact component={CreateClaim} />
            <Route path="/myclaim/:id/:index" exact component={CreateClaim} />
            <Route path="/myclaim/:info" exact component={CreateClaim} />
            <Route path="/myclaim" component={CreateClaim} /> */}
            <Route path="/myclaim/:id/:index/:type" exact component={EClaimSDK} />
            <Route path="/myclaim/:id/:index" exact component={EClaimSDK} />
            <Route path="/myclaim/:info" exact component={EClaimSDK} />
            <Route path="/myclaim" component={EClaimSDK} />
            <Route path="/eclaim-sdk" component={EClaimSDK} />
            <Route path="/cla/:id" exact component={SDKReceiveLink} />
            <Route path="/claim" component={CreateClaimSDK} />
            
            <Route path="/info-required-claim" component={AdditionalClaim} />
            <Route path="/payment-history" component={PaymentHistory} />
            <Route path="/point-exchange" component={ExchangePoint} />
            <Route path="/special" component={SpecialProgramPoint} />
            <Route path="/point" exact component={NormalProgramPoint} />
            <Route path="/point/:x" exact component={NotFound} />
            <Route path="/diem-thuong" component={NormalProgramPoint} >
                <Redirect to="/point" />
            </Route>
            <Route path="/gift-cart" component={GiftCart} />
            <Route path="/point-history" component={HistoryPoint} />
            <Route path="/category/:id" component={ExchangePointGeneric} />

            <Route path="/claim-history" component={ClaimHistory} />
            <Route path="/update-contact-info" component={UpdateContactInfo} />
            <Route path="/update-contact-info-change" component={UpdateContactInfo} >
                <Redirect to="/update-contact-info" />
            </Route>

            <Route path="/thong-bao" component={NotificationPage} />

            <Route path="/update-policy-info/:info" exact component={UpdatePolicyInfo} />
            <Route path="/update-policy-info" component={UpdatePolicyInfo} />

            <Route path="/reinstatement/:info" exact component={Reinstatement} />
            <Route path="/reinstatement" component={Reinstatement} />
            <Route path="/reinstatement-change" component={Reinstatement} >
                <Redirect to="/reinstatement" />
            </Route>
            
            <Route path="/payment-contract/:info" exact component={PaymentContract} />
            <Route path="/payment-contract" component={PaymentContract} />

            <Route path="/payment-contract-change" component={PaymentContract} >
                <Redirect to="/payment-contract" />
            </Route>

            <Route path="/update-policy-info-change" component={UpdatePolicyInfo} >
                <Redirect to="/update-policy-info" />
            </Route>

            <Route path="/mypayment/:id" exact component={MyPayment} />
            <Route path="/mypayment" exact component={MyPayment} />
            <Route path="/familypayment" component={FamilyPayment} />

            <Route path="/PAGE_DPBH" component={MyPayment} />
            <Route path="/PAGE_YCQL" component={CreateClaim} />
            <Route path="/PAGE_TDYC" component={ClaimInfo} />

            <Route path="/PAGE_HAPPY/" component={HealthNews} />
            <Route path="/healthnews/:id" component={HealthNews} />
            <Route path="/healthnews/" component={HealthNews} />
            <Route path="/healthnewsitem/:id" component={HealthNewsDetail} />
            <Route path="/maintainence" component={Maintainence} />
            <Route path="/home" component={App} >
                <Redirect to="/" />
            </Route>
            <Route path="/special-new" component={ExchangePointSpecialNew} />
            <Route path="/account" component={AccountInfo} />
                <Route path="/followup-claim-info/:cliID" component={ClaimInfo} />
            <Route path="/followup-claim-info" component={ClaimInfo} />
            <Route exact path="/gift-card-redirect">
                <Redirect to="/gift-cart" />
            </Route>
            <Route path="/epolicy" component={EDocumentEpolicy} />
            <Route path="/manage-epolicy" component={EDocumentInvoice} />
            <Route path="/view-invoice/:id" component={ComEDocumentInvoice} />
            <Route path="/hccard" component={HCCard} />

            {/* Utilities */}
            <Route path="/utilities" exact component={Utilities} />
            <Route path="/utilities/network/:id" exact component={NetWork} />
            <Route path="/utilities/network" exact component={NetWork} />
            <Route path="/tien-ich/mang-luoi" component={NetWork} >
                <Redirect to="/utilities/network" />
            </Route>
            <Route path="/utilities/participate" exact component={InsParticipation} />
            <Route path="/utilities/claim-guide" exact component={ClaimProcessGuide} />
            <Route path="/utilities/policy-trans" exact component={PolicyTrans} />
            <Route path="/utilities/clauses" exact component={Clauses} />
            <Route path="/utilities/policy-payment" exact component={PolicyPayment} />
            <Route path="/utilities/policy-payment-la" exact component={PolicyPayment} />
            <Route path="/utilities/faq" exact component={Faq} />
            <Route path="/utilities/document" exact component={Document} />
            <Route path="/utilities/document/:key" exact component={Document} />
            <Route path="/utilities/occupation" exact component={Occupation} />
            <Route path="/utilities/contact/:id" exact component={Contact} />
            <Route path="/utilities/contact" exact component={Contact} />
            <Route path="/utilities/feedback" exact component={Feedback} />
            <Route path="/terms-of-use" exact component={Terms} />
            <Route path="/privacy-policy" exact component={PrivacyPolicy} />
            <Route path="/claim-la/:id" exact component={AdditionalClaimPublic} />
            <Route path="/claim-la" component={AdditionalClaimPublic} />
            <Route path="/e-signature" component={EpolicyGeneral} />
            <Route path="/c/:id" component={CustomerCheck} />
            <Route path="/r/:id" component={VideoCustomer} />

            <Route path="/13/:id" component={PasscodeCheck} />
            <Route path="/tt/:id" component={PasscodeCheckPayment} />

            <Route path="/survey" component={SurveyForm} />

            <Route path="/nd13" component={ND13} />
            <Route path="/pay-mode" component={ChangePayMode} />
            <Route path="/decrease-sa" component={DecreaseSACancelRider} />
            <Route path="/payment" component={ChangePayment} />
            <Route path="/change-sa" component={ChangeSundryAmount} />
            <Route path="/update-personal-info/:id" exact component={UpdatePersonalInfo} />
            <Route path="/update-personal-info" component={UpdatePersonalInfo} />
            <Route path="/khach-hang-can-biet" component={CustomerNeedKnow} />
            <Route path="/dconnect-sdk" component={DConnectSDK} />
            <Route path="/eclaim-sdk" component={EClaimSDK} />
            <Route path="/create-claim-request" component={CreateRequest} />

            <Route path="/edoc-viewer" component={EDocumentViewer} />
            
            {/*Health & Wellbeing */}
            <Route path="/timkiem" component={SearchArticle} />
            <Route path="/tim">
                <Redirect to="/timkiem" />
            </Route>

            <Route path="/song-vui-khoe/" exact component={HealthLife} />
            <Route path="/tags/:alias" exact component={Tag} />
            <Route path="/song-vui-khoe/bi-quyet" exact component={HealthLifeSecret} />
            <Route path="/song-vui-khoe/bi-quyet/:category" exact component={Category} />
            <Route path="/song-vui-khoe/bi-quyet/:category/:article" exact component={SubCategory}/>
            <Route path="/song-vui-khoe/bi-quyet/:category/:article/:link" exact component={Article}/>
            <Route path="/song-vui-khoe/thu-thach-song-khoe" exact component={ChallengeList}/>
            <Route path="/song-vui-khoe/thu-thach-song-khoe/:id" exact component={ChallengeDetail}/>
            <Route path="/song-vui-khoe/cung-duong-yeu-thuong" exact component={CDYT}/>
            <Route path="/404" exact component={NotFound} />
            <Route path="/:x" exact component={NotFound} />
            <Route path="/*/*" exact component={NotFound} />
        </Switch>
        </ErrorBoundary>
    );
};

export default Routes;
