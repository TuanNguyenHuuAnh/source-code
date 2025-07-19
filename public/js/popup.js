// ---- Common Frame -------------------------------

const PopupFrame = document.querySelectorAll(".popup");
// const ClosePopup = document.querySelectorAll(".popup .header .closebtn");

window.addEventListener("resize", () => {
  if (window.innerWidth >= 1024) {
    PopupFrame.forEach((item) => {
      if (!item.classList.contains("special")) {
        item.classList.contains("show") && removeShowPopup(item);
      }
    });
  }
});

PopupFrame.forEach((item) => {
  const bg = item.querySelector(".popupbg");
  const ClosePopupBtn = item.querySelector(".header .closebtn");
  const ClosePopupSecond = item.querySelector(".close-btn");
  const closeSpe = item.querySelector(" .body-icon .closebtn");
  const closePop10 = item.querySelector(" .quyen-loi-death-card-close-btn .closebtn");
  const closePopDetail = item.querySelector(" .detail-sick-card-close-btn .closebtn");

  bg &&
    bg.addEventListener("click", () => {
      removeShowPopup(item);
    });

  ClosePopupBtn &&
    ClosePopupBtn.addEventListener("click", () => {
      removeShowPopup(item);
    });
  closePop10 &&
    closePop10.addEventListener("click", () => {
      removeShowPopup(item);
    });
  closePopDetail &&
    closePopDetail.addEventListener("click", () => {
      removeShowPopup(item);
    });

  closeSpe &&
    closeSpe.addEventListener("click", () => {
      removeShowPopup(item);
    });

  ClosePopupSecond &&
    ClosePopupSecond.addEventListener("click", () => {
      removeShowPopup(item);
    });
});

// function closeBtn(item) {
//   item.classList.contains("show") && item.classList.remove("show");
// }

// ----------- Utilities ---------------------------------------------

function removeShowPopup(element) {
  element && element.classList.remove("show");
}

// --------------------------------- Specific Popup Section -------------------------------------------------------

// ------------------------ Step Form ----------------------------------------------

// --------------- Step 1 ----------------------------------------------------------

const outOfEventPopup = document.querySelector(".popup.out-of-event");
const outOfEventPopupCloseBtn = document.querySelector(".out-of-event-information .out-of-event-information-close-btn");
const outOfEventChoseAgainBtn = document.querySelector(".out-of-event-information .chose-again-btn");
const outOfEventCancelBtn = document.querySelector(".out-of-event-information .cancel-btn");

outOfEventPopupCloseBtn &&
  outOfEventPopupCloseBtn.addEventListener("click", () => {
    outOfEventPopup.classList.remove("show");
  });

outOfEventChoseAgainBtn &&
  outOfEventChoseAgainBtn.addEventListener("click", () => {
    outOfEventPopup.classList.remove("show");
  });

outOfEventCancelBtn &&
  outOfEventCancelBtn.addEventListener("click", () => {
    outOfEventPopup.classList.remove("show");
  });

// ------------ Step 3 ------------------------------------

// --- hoso ---

const hosoPopup = document.querySelector(".hoso-popup");
const invokeHoso = document.querySelectorAll(".invoke-hoso");
const hosoCloseBtn = document.querySelector(".hoso-information-card .close-btn");

invokeHoso &&
  invokeHoso.forEach((item) => {
    item.addEventListener("click", () => {
      hosoPopup.classList.add("show");
    });
  });

hosoCloseBtn &&
  hosoCloseBtn.addEventListener("click", () => {
    hosoPopup.classList.contains("show") ? hosoPopup.classList.remove("show") : null;
  });

// --- notice ---

const noticePopup = document.querySelector(".popup.notice");
const noticeCloseBtn = document.querySelector(".popup.notice .notice-card-close-btn");

noticeCloseBtn &&
  noticeCloseBtn.addEventListener("click", () => {

    noticePopup.classList.remove("show");
  });

// --------------- Step 4 --------------------------------------------

// --- Confirm all Step ------------

const confirmPopup = document.querySelector(".popup.confirm");
const confirmPopupCloseBtn = document.querySelector(".popup.confirm .confirm-card-close-btn");

confirmPopupCloseBtn &&
  confirmPopupCloseBtn.addEventListener("click", () => {
    confirmPopup.classList.remove("show");
  });

// ------------------------------ Login Popup ----------------------------------------------------------------

const loginBtn = document.querySelector(".login-btn");
const loginCloseBtn = document.querySelector(".login .login__card .login-closebtn");
const loginPopup = document.querySelector(".login");

window.addEventListener("resize", () => {
  if (window.innerWidth >= 1024) {
    if (loginPopup && loginPopup.classList.contains("show")) {
      loginPopup.classList.remove("show");
    }
  }
});

loginBtn &&
  loginBtn.addEventListener("click", () => {
    loginPopup && loginPopup.classList.add("show");
  });

loginCloseBtn &&
  loginCloseBtn.addEventListener("click", () => {
    removeShowPopup(loginPopup);
  });

// -------------------------- Signup Popup ---------------------------------------------------------------------------

const optionPopup = document.querySelector(".option-popup");
const NonExistedPopup = document.querySelector(".signup-popup-nonexisted");
const ExistedPopup = document.querySelector(".signup-popup-existed");
const signupBtn = document.querySelector(".signup span");
const optionPopupCloseBtn = document.querySelector(".option-closebtn");
const CaseExistedBtn = document.getElementById("existed");
const CaseNonExistedBtn = document.getElementById("nonexisted");

signupBtn &&
  signupBtn.addEventListener("click", () => {
    removeShowPopup(loginPopup);
    optionPopup && optionPopup.classList.add("show");
  });

optionPopupCloseBtn &&
  optionPopupCloseBtn.addEventListener("click", () => {
    removeShowPopup(optionPopup);
  });

// ------------------------ Existed User ------------------------------------------

CaseExistedBtn &&
  CaseExistedBtn.addEventListener("click", () => {
    removeShowPopup(optionPopup);
  });

// ----------- NonExisted User -----------------------------

CaseNonExistedBtn &&
  CaseNonExistedBtn.addEventListener("click", () => {
    removeShowPopup(optionPopup);
    NonExistedPopup && NonExistedPopup.classList.add("show");
  });

// ----------- Existed User -----------------------------

CaseExistedBtn &&
  CaseExistedBtn.addEventListener("click", () => {
    removeShowPopup(optionPopup);
    ExistedPopup && ExistedPopup.classList.add("show");
  });

// --------------- Confirm Envelop Popup -------------------------------

const envelopConfirm = document.querySelector(".envelop-confirm-popup");
const envelopConfirmCloseBtn = document.querySelector(".envelop-confirm-card .envelopcard .closebtn");

envelopConfirmCloseBtn &&
  envelopConfirmCloseBtn.addEventListener("click", () => {
    envelopConfirm && envelopConfirm.classList.remove("show");
  });

// --------------- 9.3 popup ---------------------------------------
const popupOpen = document.querySelector(".info");
const popupClose = document.querySelector(".btn");

popupOpen &&
  popupOpen.addEventListener("click", () => {
    const queryOpen = document.querySelector(".dia-chi-phong-kham");
    queryOpen && queryOpen.classList.add("show");
  });

popupClose &&
  popupClose.addEventListener("click", () => {
    const queryClose = document.querySelector(".dia-chi-phong-kham");
    queryClose && queryClose.classList.remove("show");
  });

//  -----------------active nap the dien thoai --------
function napThe() {
  let chooseMobi = function () {
    let ele = document.querySelectorAll("[data-tab]");
    for (let i = 0; i < ele.length; i++) {
      ele[i].addEventListener("click", changeIco, false);
    }
  };

  let clearIcon = function () {
    let ele = document.querySelectorAll("[data-tab]");
    for (let i = 0; i < ele.length; i++) {
      ele[i].classList.remove("active");
      let id = ele[i].getAttribute("data-tab");
      document.getElementByClas(id).classList.remove("active");
    }
  };

  let changeIco = function (e) {
    clear();
    e.target.classList.add("active");
    let id = e.currentTarget.getAttribute("data-tab");
    document.getElementById(id).classList.add("active");
  };

  bindAll();
}

// ------------------ 2.2 Hop dong - Popup Envelop ------------------------

const envelopClose = document.querySelector(".envelop-infomation__close-button");
const envelop = document.querySelector(".envelop");

envelopClose &&
  envelopClose.addEventListener("click", () => {
    envelop.classList.remove("show");
  });

// ------------------ 3.1 Popup dieu khoan ------------------------------

const billPolicyCloseBtn = document.querySelector(".bill-policy__close-button");
const billPolicy = document.querySelector(".bill-policy-popup");

billPolicyCloseBtn &&
  billPolicyCloseBtn.addEventListener("click", () => {
    billPolicy.classList.remove("show");
  });

// ---------------- 9.1 Exchange Point Rule -----------------------------------------------------------
const popupExchangePointRule = document.querySelector(".point-exchange-rule-popup");
const exchangePointInvoke = document.querySelector(".point-rule-invoke");

exchangePointInvoke &&
  exchangePointInvoke.addEventListener("click", () => {
    popupExchangePointRule && popupExchangePointRule.classList.add("show");
  });

// --------------- Refund Profile Popup ----------------------------------------

const refundProfilePopup = document.querySelector(".refund-profile-popup");
const refundProfilePopupCloseBtn = document.querySelector(".refund-profile-card .close-btn");

refundProfilePopupCloseBtn &&
  refundProfilePopupCloseBtn.addEventListener("click", () => {
    removeShowPopup(refundProfilePopup);
  });

// --------------- Refund Confirm Popup ----------------------------------------

const refundConfirmPopup = document.querySelector(".refund-confirm-popup");
const refundConfirmPopupCloseBtn = document.querySelector(".refund-confirm-card .close-btn");
const refundConfirmPopupContinueBtn = document.querySelector(".refund-confirm-card .btn-wrapper .continue");

refundConfirmPopupCloseBtn &&
  refundConfirmPopupCloseBtn.addEventListener("click", () => {
    removeShowPopup(refundConfirmPopup);
  });

// ------------------ 3.3 Popup Khach hang tiem nang ------------------------------

const potentialCustomersCloseBtn1 = document.querySelector(".potential-customers__close-button");
const potentialCustomersCloseBtn2 = document.querySelector(".exit");
const potentialCustomers = document.querySelector(".potential-customers-popup");

potentialCustomersCloseBtn1 &&
  potentialCustomersCloseBtn1.addEventListener("click", () => {
    potentialCustomers.classList.remove("show");
  });
potentialCustomersCloseBtn2 &&
  potentialCustomersCloseBtn2.addEventListener("click", () => {
    potentialCustomers.classList.remove("show");
  });

// -------------------------- Remove Cart Popup -------------------------------------

const removeCartPopup = document.querySelector(".remove-cart-popup");
// const removeCartPopupCloseBtn = document.querySelector(".remove-cart-popup .closebtn");
const removeCartPopupCancelBtn = document.querySelector(".remove-cart-popup .no-btn");

removeCartPopupCancelBtn &&
  removeCartPopupCancelBtn.addEventListener("click", () => {
    removeShowPopup(removeCartPopup);
  });

// ------------------------ Phone Confirm Popup ------------------------------------------

const phoneConfirmPopup = document.querySelector(".phone-confirm-popup");
const phoneConfirmPopupCloseBtn = document.querySelector(".phone-confirm-popup .close-btn");
