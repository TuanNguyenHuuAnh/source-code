// ------ Sidebar ----------------------------------------------------

// const sidebarDropdown = document.querySelectorAll(".sidebar .sidebar__navigation .sidebar-item.dropdown");

// sidebarDropdown.forEach((item) => {
//   const btn = item.querySelector(".dropdown__content");
//   btn.addEventListener("click", () => {
//     item.classList.toggle("show");
//   });
// });

const profileBtn = document.querySelector("#profilebtn");
const sidebar = document.querySelector(".sidebar");
const sidebarWarpper = document.querySelector(".sidebar-warpper");
const closeSidebarBtn = document.querySelector(".sidebar .sidebar-close");

profileBtn.addEventListener("click", () => {
  sidebar.classList.add("mobile");
  sidebarWarpper.classList.add("mobile");
});

closeSidebarBtn &&
  closeSidebarBtn.addEventListener("click", () => {
    sidebar.classList.remove("mobile");
    sidebarWarpper.classList.remove("mobile");
  });

window.addEventListener("resize", () => {
  if (window.innerWidth >= 1024) {
    if (sidebar && sidebar.classList.contains("mobile")) {
      sidebar.classList.remove("mobile");
      sidebarWarpper.classList.remove("mobile");
    }
  }
});
