// --- Banner ------------------
const nextbtn = document.querySelector(".next");
const nextBtn = document.querySelector(".custom-carousel .control .next");
const preBtn = document.querySelector(".custom-carousel .control .prev");
const bannerSlides = document.querySelectorAll(".custom-carousel .carousel__item");
const bannerPaging = document.querySelector(".custom-carousel .banner-paging");

nextbtn &&
  nextbtn.addEventListener("click", () => {
    // //console.log("hello");
    bannerCarousel.slideNext(500, true);
  });

const bannerCarousel = new Swiper(".custom-carousel", {
  speed: 500,
  spaceBetween: 30,
  loop: true,
  autoplay: {
    delay: 4000
  }
});

nextBtn &&
  nextBtn.addEventListener("click", () => {
    bannerCarousel.slideNext();
  });
preBtn &&
  preBtn.addEventListener("click", () => {
    bannerCarousel.slidePrev();
  });

bannerSlides.forEach((__, index) => {
  const dot = document.createElement("div");
  dot.setAttribute("class", "dot");
  dot.addEventListener("click", () => {
    bannerCarousel.slideTo(index + 1, 500);
  });
  bannerPaging.appendChild(dot);
});

bannerPaging && bannerPaging.children[bannerCarousel.realIndex].classList.add("active");

bannerCarousel.on("slideChange", () => {
  const dot = bannerPaging.querySelectorAll(".dot");

  dot.forEach((item) => {
    item !== dot[bannerCarousel.realIndex] ? item.classList.remove("active") : item.classList.add("active");
  });
});

// ------------------------ Folder HomePage --------------------------------------------

const folderCarousel = new Swiper(".folder", {
  speed: 500,
  // spaceBetween: 20,
  navigation: {
    nextEl: ".folder-nav-next",
    prevEl: ".folder-nav-pre",
    disabledClass: "disabled"
  },
  breakpoints: {
    320: {
      slidesPerView: 1,
      spaceBetween: 40
    },
    1024: {
      slidesPerView: 2,
      spaceBetween: 20
    }
  }
});

const folderWarpper = document.querySelector(".folderwarpper");

if (folderCarousel.slides.length === 1) {
  folderWarpper.classList.add("mid");
}

// --------------------- Radar Carousel ------------------------------------------------

const radarCarousel = new Swiper(".radarcarousel", {
  speed: 500,
  pagination: {
    el: ".radarcarousel-pagination",
    type: "bullets",
    clickable: true,
    bulletActiveClass: "radar-pagi-active"
  }
});

// ------------------- Plan Carousel ---------------------------------------------

const planCarousel = new Swiper(".plancarousel", {
  speed: 500,
  breakpoints: {
    320: {
      slidesPerView: 1,
      spaceBetween: 20
    },
    1024: {
      slidesPerView: 2,
      spaceBetween: 20
    }
  }
});

// ------------------ Product Carousel Page ---------------------------------------

const paging = document.querySelectorAll(".interest-wrapper .interest-paging .paging-tab");

const productInterestCarousel = new Swiper(".interest-carousel", {
  speed: 500
});

paging && paging[productInterestCarousel.realIndex]?.classList.add("active");

paging &&
  paging.forEach((item, index) => {
    // //console.log("asdf", item);

    item.addEventListener("click", () => {
      productInterestCarousel.slideTo(index);

      // item === paging[productInterestCarousel.realIndex] &&
    });
  });

productInterestCarousel &&
  productInterestCarousel.on("slideChange", () => {
    paging.forEach((item) => {
      item !== paging[productInterestCarousel.realIndex]
        ? item.classList.remove("active")
        : item.classList.add("active");
    });
  });

// --------------------------- Additional Products Carousel --------------------------------------------

const additionalProductCarousel = new Swiper(".additional-products-carousel", {
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev"
  },
  speed: 500,
  breakpoints: {
    320: {
      slidesPerView: 1,
      spaceBetween: 20
    },
    1024: {
      slidesPerView: 2,
      spaceBetween: 60
    }
  }
});

// ------------------------ Product Banner Main Carousel --------------------------------------------------

const productMainCarousel = new Swiper(".product-carousel-main", {
  speed: 500,
  pagination: {
    el: ".product-carousel-main .main-pagination",
    type: "bullets"
  }
});
