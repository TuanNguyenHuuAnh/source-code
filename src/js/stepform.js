// --------- Constant ----------------------

const validateImage = ["image/jpeg", "image/jpg", "image/png"];

const validateFile = ["application/pdf"];

const imgElement = (url) => {
  return `
  <div class="file">
  <div class="img-wrapper">
    <img src=${url} alt="" />
  </div>
  <div class="deletebtn">X</div>
</div>
  `;
};

// -------------------- Checkbox Dropdown -----------------------------------

const checkboxWarpperDropdown = document.querySelectorAll(".checkbox-wrap .tab-wrapper .tab");

//console.log(checkboxWarpperDropdown);

checkboxWarpperDropdown &&
  checkboxWarpperDropdown.forEach((tab) => {
    const checkbox = tab.querySelector(".round-checkbox .customradio input");
    const dropdown = tab.querySelector(".checkbox-dropdown");

    //console.log("adf", checkbox);

    //console.log(dropdown);

    checkbox &&
    checkbox.addEventListener("click", (e) => {
        //console.log("hello", e);
      e.target.checked ? dropdown.classList.add("show") : dropdown.classList.remove("show");
    });
  });

// ------------- Image Upload -------------------------------------------

const allDropArea = document.querySelectorAll(".img-upload-wrapper");

let files;

allDropArea &&
  allDropArea.forEach((content) => {
    const dropArea = content.querySelector(".img-upload-item .img-upload");

    countItem(content);

    // --- Click on Object -------------------

    const inputbtn = content.querySelector(".inputfile");

    dropArea.addEventListener("click", () => {
      inputbtn.click();
    });

    inputbtn.addEventListener("change", (e) => {
      e.preventDefault();

      files = Object.values(inputbtn.files);

      processFile(files, content);
    });

    // --- drag file over area ------------

    dropArea.addEventListener("dragover", (e) => {
      e.preventDefault();

      dropArea.classList.add("active");
    });

    // --- leave drag file over area ------------

    dropArea.addEventListener("dragleave", (e) => {
      e.preventDefault();

      dropArea.classList.remove("active");
    });

    // --- drop file -----------

    dropArea.addEventListener("drop", (e) => {
      e.preventDefault();

      files = Object.values(e.dataTransfer.files);

      processFile(files, content);
    });
  });

// ------ Utilities --------------------------

function countItem(content) {
  let numItem = content.querySelectorAll(".img-upload-item");

  numItem && numItem.length > 1 ? content.classList.add("not-empty") : content.classList.remove("not-empty");
}

function deleteItem(contents) {
  let files = contents.querySelectorAll(".img-upload-item .file");

  files &&
    files.forEach((item) => {
      const deleteBtn = item.querySelector(".deletebtn");

      deleteBtn.addEventListener("click", (e) => {
        const img = e.target.parentNode.parentNode;
        img.remove();
        countItem(contents);
      });
    });
}

function processFile(files, element) {
  files?.map((file) => {
    let fileType = file.type;

    if (validateImage.includes(fileType)) {
      let fileReader = new FileReader(); // --- Create file reader object ---------------

      fileReader.onload = () => {
        let fileUrl = fileReader.result;

        const UploadItem = document.createElement("div");
        UploadItem.setAttribute("class", "img-upload-item");

        let imgTag = imgElement(fileUrl); // --- creating img tag and passing user selected file src inside src

        UploadItem.innerHTML = imgTag;

        element.appendChild(UploadItem);
      };

      fileReader.onloadend = () => {
        countItem(element);

        // let files = element.querySelectorAll(".img-upload-item");

        // //console.log("elemnet", files);

        deleteItem(element);
      };

      fileReader.readAsDataURL(file);
    }
  });
}
