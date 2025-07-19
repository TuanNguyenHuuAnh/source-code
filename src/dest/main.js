// ------------- Utilities ---------------------------

function closeShow(element) {
  element.classList.remove("show");
}

// --------- Common -------------------------------------------

const dropdown = document.querySelectorAll(".dropdown");

dropdown &&
  dropdown.forEach((item) => {
    const btn = item.querySelector(".dropdown__content");
    btn.addEventListener("click", () => {
      item.classList.contains("disabled") ? null : item.classList.toggle("show");
    });
  });

// ------ Header --------------------------------------------------

const header = document.querySelector("header");
const burger = document.querySelector(".burger");
const HeaderBG = document.querySelector("header .headerbg");
const bodyBG = document.querySelector(".body .body-icon ");
const closeNavBtn = document.querySelector(".mobile-navigation .mobile-navigation__header .closebtn");

burger &&
  burger.addEventListener("click", () => {
    header.classList.add("mobile");
  });
closeNavBtn &&
  closeNavBtn.addEventListener("click", () => {
    header.classList.remove("mobile");
  });

window.addEventListener("resize", () => {
  if (window.innerWidth >= 1200) {
    //console.log("hello");

    if (header.classList.contains("mobile")) {
      header.classList.remove("mobile");
    }
  }
});

HeaderBG &&
  HeaderBG.addEventListener("click", () => {
    header.classList.remove("mobile");
  });

// ---------------------------- Card ---------------------------------

const cards = document.querySelectorAll(".card");

cards.forEach((card) => {
  card.addEventListener("click", () => {
    card.classList.toggle("choosen");
  });
});

// -----------------------------Contract List Menu (2.1) ---------------------

const contractListMenu = document.querySelector(".contract-list-menu");

const checkComponent = () => {
  for (let i = 0; i < contractListMenuItems.length; i++) {
    if (contractListMenuItems[i].classList.contains("contract-list-menu__item__active")) {
      switch (i) {
        case 0:
          contractFee && contractFee.classList.add("show-component");
          break;
        case 1:
          contractProducts && contractProducts.classList.add("show-component");
          break;
        case 2:
          contractValue && contractValue.classList.add("show-component");
          break;
        case 3:
          contractBeneficiary && contractBeneficiary.classList.add("show-component-flex");
          break;
        case 4:
          contractAdvise && contractAdvise.classList.add("show-component");
          break;
        default:
          break;
      }
    } else {
      switch (i) {
        case 0:
          contractFee && contractFee.classList.remove("show-component");
          break;
        case 1:
          contractProducts && contractProducts.classList.remove("show-component");
          break;
        case 2:
          contractValue && contractValue.classList.remove("show-component");
          break;
        case 3:
          contractBeneficiary && contractBeneficiary.classList.remove("show-component-flex");
          break;
        case 4:
          contractAdvise && contractAdvise.classList.remove("show-component");
          break;
        default:
          break;
      }
    }
  }
};

const contractListMenuItems = document.querySelectorAll(".contract-list-menu__item");

const contractFee = document.querySelector(".contract-fee");
const contractProducts = document.querySelector(".contract-products");
const contractValue = document.querySelector(".contract-value");
const contractBeneficiary = document.querySelector(".contract-beneficiary");
const contractAdvise = document.querySelector(".contract-advise");

contractListMenuItems &&
  contractListMenuItems.forEach((item, index) => {
    checkComponent();
    item.addEventListener("click", () => {
      item.classList.toggle("contract-list-menu__item__active");
      contractListMenuItems.forEach((none) => {
        if (none !== item) {
          none.classList.remove("contract-list-menu__item__active");
        }
      });
      checkComponent();
    });
  });

// ------- Money Input Autocomplete ------------------------------------

const moneyArray = ["200.000 VNĐ", "2.000.000 VNĐ", "20.000.000 VNĐ"];
// //console.log("hafd");

const money = document.querySelectorAll(".money");
const moneyInput = document.querySelector(".money .input__content input");

function autoComplete(inp, arr) {
  const autoCompleteWarpper = inp.querySelector(".money__autocomplete");

  inp.addEventListener("input", function (e) {
    const value = e.target.value;

    //console.log("value", value);

    autoCompleteWarpper.innerHTML = "";

    if (!value) {
      return;
    }

    arr.forEach((item) => {
      if (item.substr(0, value.length) === value) {
        const autoCompleteItem = document.createElement("div");
        const autocCompleteContent = document.createElement("p");
        autoCompleteItem.setAttribute("class", "money__autocomplete-item");
        autocCompleteContent.innerHTML = item;
        autoCompleteItem.appendChild(autocCompleteContent);
        autocCompleteContent.addEventListener("click", () => {
          moneyInput.value = item;
          autoCompleteWarpper.innerHTML = "";
        });
        autoCompleteWarpper.appendChild(autoCompleteItem);
      }
    });
  });
}

money.forEach((content) => {
  autoComplete(content, moneyArray);
});

// ------------ Present Dropdown ----------------------------------

const SpecialDropdown = document.querySelectorAll(".special-dropdown");

SpecialDropdown &&
  SpecialDropdown.forEach((dropdown) => {
    // const ignoreElement = dropdown.querySelector(".dropdown__items");
    const DropdownBtn = dropdown.querySelector(".dropdown__content");
    const bg = dropdown.querySelector(".bg-dropdown");
    const closeBtn = dropdown.querySelector(".dropdown__items .close");
    const tabs = dropdown.querySelectorAll(".dropdown__items .tab-item-warpper .tab-item");
    const content = dropdown.querySelector(".dropdown__content .tab-content p");

    document.addEventListener("click", (e) => {
      const isInside = dropdown.contains(e.target);
      !isInside && closeShow(dropdown);
    });

    bg.addEventListener("click", () => {
      closeShow(dropdown);
    });

    DropdownBtn.addEventListener("click", () => {
      dropdown.classList.toggle("show");
    });
    closeBtn.addEventListener("click", () => {
      closeShow(dropdown);
    });

    tabs.forEach((tab) => {
      tab.addEventListener("click", () => {
        tabs.forEach((none) => {
          none !== tab && none.classList.remove("choosen");
        });
        content.innerHTML = tab.dataset.tab;
        tab.classList.add("choosen");
        closeShow(dropdown);
      });
    });
  });

// ------------------------ Bottom Dropdown -------------------------------------------------------

const BottomDropdown = document.querySelectorAll(".bottomdropdown");

BottomDropdown &&
  BottomDropdown.forEach((dropdown) => {
    // const ignoreElement = dropdown.querySelector(".dropdown__items");
    const DropdownBtn = dropdown.querySelector(".dropdown__content");
    const bg = dropdown.querySelector(".dropdownbg");
    const closeBtn = dropdown.querySelector(".dropdown__items .close");
    const tabs = dropdown.querySelectorAll(".dropdown__items .bottomdropdown-tab");
    const content = dropdown.querySelector(".dropdown__content p");

    document.addEventListener("click", (e) => {
      const isInside = dropdown.contains(e.target);
      !isInside && closeShow(dropdown);
    });

    bg.addEventListener("click", () => {
      closeShow(dropdown);
    });

    // DropdownBtn.addEventListener("click", () => {
    //   dropdown.classList.toggle("show");
    // });
    closeBtn.addEventListener("click", () => {
      closeShow(dropdown);
    });

    tabs.forEach((tab) => {
      tab.addEventListener("click", () => {
        tabs.forEach((none) => {
          none !== tab && none.classList.remove("active");
        });
        content.classList.contains("basic-black") ? null : content.classList.add("basic-black");
        content.innerHTML = tab.dataset.tab;
        tab.classList.add("active");
        closeShow(dropdown);
      });
    });
  });

// ---------------------------------- 9.1 Component (Menulist) ---------------------------------
const checkComponent91 = () => {
  for (let i = 0; i < accumulatePointMenuItems.length; i++) {
    if (accumulatePointMenuItems[i].classList.contains("accumulate-points__menu-list__item-active")) {
      switch (i) {
        case 0:
          person.classList.add("show-component");
          break;
        case 1:
          heart.classList.add("show-component");
          break;
        case 2:
          silver.classList.add("show-component");
          break;
        case 3:
          gold.classList.add("show-component");
          break;
        case 4:
          diamond.classList.add("show-component");
          break;
        default:
          break;
      }
    } else {
      switch (i) {
        case 0:
          person.classList.remove("show-component");
          break;
        case 1:
          heart.classList.remove("show-component");
          break;
        case 2:
          silver.classList.remove("show-component");
          break;
        case 3:
          gold.classList.remove("show-component");
          break;
        case 4:
          diamond.classList.remove("show-component");
          break;
        default:
          break;
      }
    }
  }
};

const accumulatePointMenuItems = document.querySelectorAll(".accumulate-points__menu-list__item");

const person = document.querySelector(".person");
const heart = document.querySelector(".heart");
const silver = document.querySelector(".silver");
const gold = document.querySelector(".gold");
const diamond = document.querySelector(".diamond");

accumulatePointMenuItems.forEach((item, index) => {
  checkComponent91();
  item.addEventListener("click", () => {
    //console.log("click");
    item.classList.toggle(".accumulate-points__menu-list__item-active");
    accumulatePointMenuItems.forEach((none) => {
      if (none !== item) {
        none.classList.remove(".accumulate-points__menu-list-active");
      }
    });
    checkComponent91();
  });
});

// --------------- 2.2 Hop dong  ----------------
// ---- Button Filter----

// --------------- Toggle Passwords ---------------------------------------

const passwordInput = document.querySelectorAll(".password-input");

passwordInput &&
  passwordInput.forEach((item) => {
    const passwordBtn = item.querySelector(".password-toggle");
    const password = item.querySelector("input");

    passwordBtn.addEventListener("click", () => {
      if (item.classList.contains("show")) {
        item.classList.remove("show");
        password.type = "password";
      } else {
        item.classList.add("show");
        password.type = "text";
      }
    });
  });

// --------------- Special Input Extend ---------------------------------------

const SpecialInputExtend = document.querySelectorAll(".special-input-extend");

SpecialInputExtend &&
  SpecialInputExtend.forEach((content) => {
    const Input = content.querySelector("input");

    const IDropdownValue = content.querySelector(".input__content p");

    // //console.log("da", Input.value);

    // //console.log("afd", IDropdownValue.innerHTML === "");

    if (Input.value !== "") {
      content.classList.add("filled");
    } else {
      content.classList.remove("filled");
    }

    content.addEventListener("click", () => {
      Input.focus();
    });

    Input.addEventListener("change", (e) => {
      e.target.value !== "" ? content.classList.add("filled") : content.classList.remove("filled");
    });
  });

// ----------------- Filter -------------------------------------------

const specialFilter = document.querySelectorAll(".specialfilter");

specialFilter &&
  specialFilter.forEach((item) => {
    const filterBtn = item.querySelector(".filter_button");
    const bg = item.querySelector(".bg");
    const allFilterPopTicks = item.querySelectorAll(".filter-pop .filter-pop-tick");
    const closeBtn = item.querySelector(".filter-pop .head .closebtn");

    document.addEventListener("click", (e) => {
      const element = item.contains(e.target);
      if (!element) {
        item.classList.remove("show");
      }
    });

    filterBtn.addEventListener("click", () => {
      item.classList.toggle("show");
    });

    closeBtn &&
      closeBtn.addEventListener("click", () => {
        item.classList.remove("show");
      });

    bg &&
      bg.addEventListener("click", () => {
        item.classList.remove("show");
      });

    allFilterPopTicks &&
      allFilterPopTicks.forEach((tick) => {
        tick &&
          tick.addEventListener("click", () => {
            tick.classList.toggle("ticked");
            allFilterPopTicks.forEach((none) => {
              if (none !== tick) {
                none.classList.remove("ticked");
              }
            });
            item.classList.remove("show");
          });
      });
  });

// ----------- Counter -----------------------------

const counters = document.querySelectorAll(".counter");

counters &&
  counters.forEach((item) => {
    const plus = item.querySelector(".plus");
    const minus = item.querySelector(".minus");

    const num = item.querySelector(".counter__num span");

    plus.addEventListener("click", () => {
      if (!plus.classList.contains("disabled")) {
        num.innerHTML = parseInt(num.innerHTML) + 1;
      }
    });
    minus.addEventListener("click", () => {
      if (!plus.classList.contains("disabled")) {
        if (parseInt(num.innerHTML) - 1 >= 0) {
          num.innerHTML = parseInt(num.innerHTML) - 1;
        }
      }
    });
  });
// -------------------------- 10.0 Components --------------------------
// ----- Policy menu --------

const policyMenu = document.querySelector(".policy-menu");
const policyMenuItems = document.querySelectorAll(".policy-menu__item");

const policyLetter = document.querySelector(".policy-letter");
const commitmentLetter = document.querySelector(".commitment-letter");

policyMenuItems.forEach((item, index) => {
  item.addEventListener("click", () => {
    item.classList.add("active");
    policyMenuItems.forEach((none) => {
      if (none !== item) {
        none.classList.remove("active");
      }
    });
    if (index === 0) {
      policyLetter.classList.add("show");
      commitmentLetter.classList.remove("show");
    } else if (index === 1) {
      commitmentLetter.classList.add("show");
      policyLetter.classList.remove("show");
    }
  });
});

// --------------- Other Option Toggle -----------------------------

const OtherOptionToggle = document.getElementById("other-option-toggle");
const main = document.querySelector("main");

OtherOptionToggle &&
  OtherOptionToggle.addEventListener("click", () => {
    main.classList.toggle("nodata");
  });
