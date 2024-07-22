///////////////////////////////////
//////////     STUDENT   //////////
///////////////////////////////////

// createStudentList();
// createInstructorList();
// createCourseList();
// createCoursePresList();
const insArr = [];
const stArr = [];
const courseArr = [];
const coursePresArr = [];
const courseSelArr = [];
$(document).ready(function () {
  loadInstructorListHTML();
  loadCoursesListHTML();
  loadCoursePresListHTML();
  loadStudentsListHTML();
});

// HELPER FUNCTIONS
const timeout = function (s) {
  return new Promise(function (_, reject) {
    setTimeout(function () {
      reject(new Error(`Request took too long! Timeout after ${s} second`));
    }, s * 1000);
  });
};
const AJAX = async function (url, uploadData = undefined) {
  try {
    console.log(url);
    const fetchPro = uploadData
      ? fetch(url, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(uploadData),
        })
      : fetch(url);
    const TIMEOUT_SEC = 15;
    const res = await Promise.race([fetchPro, timeout(TIMEOUT_SEC)]);
    const data = await res.json();

    if (!res.ok) throw new Error(`${data.message} (${res.status})`);
    return data;
  } catch (err) {
    throw err;
  }
};
const AJAXMet = async function (url, method) {
  try {
    console.log(url);
    const fetchPro = fetch(url, { method: method });
    const TIMEOUT_SEC = 15;
    const res = await Promise.race([fetchPro, timeout(TIMEOUT_SEC)]);
    const data = await res.json();

    if (!res.ok) throw new Error(`${data.message} (${res.status})`);
    return data;
  } catch (err) {
    throw err;
  }
};

// MODEL FUNCTIONS --------------------------------
const loadInstructorListHTML = async function () {
  const result = await AJAX("/IE-Uni/instructor");
  result.instructors.map((e) =>
    insArr.push(Object.assign(new Instrauctor(), e))
  );
  createInstructorList();
};

async function loadCoursesListHTML() {
  const result = await AJAX("/IE-Uni/course");
  result.courses.map((e) => courseArr.push(Object.assign(new Course(), e)));
  createCourseList();
}

async function loadCoursePresListHTML() {
  const result = await AJAX("/IE-Uni/coursepres");
  if (result.status === "success") {
    result.coursesPreses.map((e) => {
      const course = new Course(
        e.course.courseId,
        e.course.title,
        e.course.unitNumber
      );
      const instructor = new Instrauctor(
        e.instructor.insCode,
        e.instructor.firstName,
        e.instructor.lastName,
        e.instructor.gender
      );
      const coursePresentation = new CoursePresentation(
        e.coursePresId,
        course,
        instructor
      );
      coursePresArr.push(coursePresentation);
    });
    createCoursePresList();
  }
}

async function loadStudentsListHTML() {
  const result = await AJAX("/IE-Uni/getstudents");
  result.students.map((e) => {
    let course, instructor, cp;
    // Map the selectedCourses array to instances of CoursePresentation
    const sC = e.selectedCourses.map((coursePres) => {
      const course = new Course(
        coursePres.course.courseId,
        coursePres.course.title,
        coursePres.course.unitNumber
      );
      const instructor = new Instrauctor(
        coursePres.instructor.insCode,
        coursePres.instructor.firstName,
        coursePres.instructor.lastName,
        coursePres.instructor.gender
      );
      const cp = new CoursePresentation(
        coursePres.coursePresId,
        course,
        instructor
      );
      return cp;
    });

    // Create a new Student instance with the parsed data
    const student = new Student(
      e.stCode,
      e.firstName,
      e.lastName,
      e.gender,
      sC
    );
    student.selectedCourses.map((cp) => {
      const courseSelected = new CourseSelected(student, cp);
      courseSelArr.push(courseSelected);
    });

    // Push the Student instance into the stArr array
    stArr.push(student);

    createStudentList();
  });
}
// CLASS //
class Student {
  constructor(stCode, firstName, lastName, gender, selectedCourses = []) {
    this.stCode = stCode;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.selectedCourses = selectedCourses;
  }
  getFullName() {
    return `${this.firstName}, ${this.lastName}`;
  }
  getGenderTitle() {
    return this.gender == 0 ? "Male" : "Female";
  }
  selectCourse(selCourse) {
    this.selectedCourses.push(selCourse);
  }

  calcTotalGrade() {
    //TODO homework for you
  }
}

// SELECTORS //
// Btns //
const btnNewStu = document.querySelector(".btnNewStu");
const btnCancel = document.querySelector(".btnCancel");
const btnSave = document.querySelector(".btnSave");

// inputs
const inputStCode = document.getElementById("stuCode");
const inputStFirstName = document.getElementById("stuFirstName");
const inputStLastName = document.getElementById("stuLastName");
const inputGender = document.getElementById("stuGender");
const inputStOldId = document.getElementById("oldStCode");

// form
const modal = document.querySelector(".modal_student");
const overlay = document.querySelector(".overlay");
const studentForm = document.getElementById("modad_student");

// list

const studentList = document.getElementById("studentList");

// EVENR LISTENERS //
btnNewStu.addEventListener("click", (e) => {
  e.preventDefault();
  initStudentDef();
});
btnCancel.addEventListener("click", initElements);
overlay.addEventListener("click", initElements);
btnSave.addEventListener("click", (e) => {
  e.preventDefault();
  saveStudent();
});

// FUNCTIONS //
function initStudentDef() {
  btnNewStu.disabled = true;
  inputStCode.value = "";
  inputStFirstName.value = "";
  inputStLastName.value = "";
  inputGender.value = 0;
  inputStOldId.value = "new";
  openModal();

  inputStCode.focus();
}
//////////////////////
function createStudentList() {
  initElements();
  if (stArr.length > 0) {
    createStudentTable();
  } else {
    studentList.innerHTML =
      "<p class='no--list'>No Students defined yet! click to create one.</p>";
  }
}
function openModal() {
  modal.classList.remove("hidden");
  overlay.classList.remove("hidden");
}

function closeModal() {
  modal.classList.add("hidden");
  overlay.classList.add("hidden");
}

function initElements() {
  closeModal();
  btnNewStu.disabled = false;
}

function createStudentTable() {
  let tbl = `<div class"section_table"><table>
       <tr>
           <th>Row No</th>
           <th>Student Code</th>
           <th>First Name</th>
           <th>Last Name</th>
           <th>Gender</th>
           <th></th>
           <th></th>
           <th></th>
       </tr>`;

  for (let i = 0; i < stArr.length; i++) {
    tbl += `<tr>
                       <td>${i + 1}</td>
                       <td>${stArr[i].stCode}</td>
                       <td>${stArr[i].firstName}</td>
                       <td>${stArr[i].lastName}</td>
                       <td>${stArr[i].getGenderTitle()}</td>
                       <td><button class="btn btnEdit" onclick="prepareEditStudent(${
                         stArr[i].stCode
                       })">Edit</button></td>
                       <td><button class="btn btnDelete" onclick="deleteStudent(${
                         stArr[i].stCode
                       })">Delete</button></td>
                       <td><button class="btn selectedCourses" onclick="showSelectedCourses(${
                         stArr[i].stCode
                       })">Courses</button></td>
                   </tr>`;
  }

  tbl += "</table></div>";

  studentList.innerHTML = tbl;
}

async function saveStudent() {
  let stCode = inputStCode.value;
  let firstName = inputStFirstName.value;
  let lastName = inputStLastName.value;
  let gender = inputGender.value;

  let oldStuCode = inputStOldId.value;
  if (validateStudentInputs(stCode, firstName, lastName, oldStuCode)) {
    if (isNaN(oldStuCode)) {
      let stu = new Student(stCode, firstName, lastName, gender);
      const result = await AJAXMet(
        `/IE-Uni/student?action=save&stCode=${stu.stCode}&firstName=${stu.firstName}&lastName=${stu.lastName}&gender=${stu.gender}`,
        "PUT"
      );
      if (result.status === "success") {
        stArr.push(stu);
      } else {
        console.log(result.message);
      }
    } else {
      oldStuCode = parseInt(oldStuCode);
      const idx = getIndexStudent(oldStuCode);
      if (idx > -1) {
        const result = await AJAXMet(
          `/IE-Uni/student?action=update&stCode=${stCode}&firstName=${firstName}&lastName=${lastName}&gender=${gender}&oldId=${oldStuCode}`,
          "PUT"
        );
        if (result.status === "success") {
          stArr[idx].stCode = stCode;
          stArr[idx].firstName = firstName;
          stArr[idx].lastName = lastName;
          stArr[idx].gender = gender;
        } else {
          console.log(result.message);
        }
      }
    }
  }
  createStudentList();
}
function validateStudentInputs(stCode, stFName, stLName, oldStCode) {
  // Check if the required fields are not empty
  if (stCode.trim() === "" || stFName.trim() === "" || stLName.trim() === "") {
    return false;
  }

  for (let i = 0; i < stArr.length; i++) {
    if (stArr[i].stCode == stCode) {
      if (isNaN(oldStCode)) {
        alert("There is a customer with this Id!");
        return false;
      } else {
        const oldIndx = getIndexStudent(oldStCode);
        const newIndx = getIndexStudent(stArr[i].stCode);
        console.log(oldIndx + " " + newIndx);
        if (oldIndx == newIndx) {
          continue;
        } else {
          alert("There is a student with this id!");
          return false;
        }
      }
    }
  }

  if (!/^\d+$/.test(stCode) || parseInt(stCode, 10) <= 0) {
    alert("Please enter a valid positive numeric value for Customer ID.");
    return false;
  }

  if (stFName.length < 2 || stLName.length < 2) {
    alert("First name and last name must be at least two characters long.");
    return false;
  }

  return true;
}

function getIndexStudent(stuCode) {
  let idx = -1;
  for (let i = 0; i < stArr.length; i++) {
    if (stArr[i].stCode == stuCode) {
      idx = i;
      break;
    }
  }
  return idx;
}

function prepareEditStudent(stuCode) {
  let stuIdx = getIndexStudent(stuCode);
  if (stuIdx > -1) {
    let stu = stArr[stuIdx];
    btnNewStu.disabled = true;
    inputStCode.value = stu.stCode;
    inputStFirstName.value = stu.firstName;
    inputStLastName.value = stu.lastName;
    inputGender.value = stu.gender;
    inputStOldId.value = stu.stCode;
    document.querySelector(".modal_student").classList.remove("hidden");
    overlay.classList.remove("hidden");
    inputStCode.focus();
  }
}

async function deleteStudent(stuCode) {
  const result = await AJAXMet(`/IE-Uni/student?id=${stuCode}`, "delete");
  if (result.status === "success") {
    const stuIdx = getIndexStudent(stuCode);
    if (stuIdx > -1) {
      stArr.splice(stuIdx, 1);
      createStudentList();
    }
  } else {
    console.log(result.message);
  }
}

///////////////////////////////////
//////////  Instructor   //////////
///////////////////////////////////

// CLASS //
class Instrauctor {
  constructor(insCode, firstName, lastName, gender) {
    this.insCode = insCode;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
  }
  getFullName() {
    return `${this.firstName}, ${this.lastName}`;
  }
  getGenderTitle() {
    return this.gender == 0 ? "Male" : "Female";
  }
}

// SELECTORS //
// Btns
const btnNewInstructor = document.querySelector(".btnNewInstructor");
const btnCancelIns = document.querySelector(".btnCancelIns");
const btnSaveIns = document.querySelector(".btnSaveIns");

// inputs
const inputInsCode = document.getElementById("insCode");
const inputInsFirstName = document.getElementById("insFirstName");
const inputInsLastName = document.getElementById("insLastName");
const inputInsGender = document.getElementById("indGender");
const inputInsOldId = document.getElementById("insOldCode");

// form

const instructorForm = document.getElementById("instructor-form");

// list

const instructorList = document.getElementById("instructor-list");

// EVENR LISTENERS //
btnNewInstructor.addEventListener("click", (e) => {
  e.preventDefault();
  initInstructortDef();
});
btnCancelIns.addEventListener("click", initInstructorElements);
btnSaveIns.addEventListener("click", (e) => {
  e.preventDefault();
  saveInstructor();
});

// FUNCTIONS //
function initInstructortDef() {
  btnNewInstructor.disabled = true;
  inputInsCode.value = "";
  inputInsFirstName.value = "";
  inputInsLastName.value = "";
  inputInsGender.value = 0;
  inputInsOldId.value = "new";

  instructorForm.style.display = "block";
  inputInsCode.focus();
}
//////////////////////
function createInstructorList() {
  initInstructorElements();
  if (insArr.length > 0) {
    createInstructorTable();
  } else {
    instructorList.innerHTML =
      "<p class='no--list'>No instructors defined yet!</p>";
  }
}

function initInstructorElements() {
  instructorForm.style.display = "none";
  btnNewInstructor.disabled = false;
}

function createInstructorTable() {
  let tbl = `<table>
       <tr>
           <th>Row No</th>
           <th>Instructor Code</th>
           <th>First Name</th>
           <th>Last Name</th>
           <th>Gender</th>
           <th></th>
           <th></th>
       </tr>`;

  for (let i = 0; i < insArr.length; i++) {
    tbl += `<tr>
                       <td>${i + 1}</td>
                       <td>${insArr[i].insCode}</td>
                       <td>${insArr[i].firstName}</td>
                       <td>${insArr[i].lastName}</td>
                       <td>${insArr[i].getGenderTitle()}</td>
                       <td><button class="btn btnEdit" onclick="prepareEditInstructor(${
                         insArr[i].insCode
                       })">Edit</button></td>
                       <td><button class="btn btnDelete" onclick="deleteInstructor(${
                         insArr[i].insCode
                       })">Delete</button></td>
                   </tr>`;
  }

  tbl += "</table>";

  instructorList.innerHTML = tbl;
}

async function saveInstructor() {
  let insCode = inputInsCode.value;
  let firstName = inputInsFirstName.value;
  let lastName = inputInsLastName.value;
  let gender = inputInsGender.value;

  let oldInsCode = inputInsOldId.value;
  if (validateInstructor(insCode, firstName, lastName, oldInsCode)) {
    if (isNaN(oldInsCode)) {
      let ins = new Instrauctor(insCode, firstName, lastName, gender);
      const result = await AJAXMet(
        `/IE-Uni/instructor?action=save&insCode=${ins.insCode}&firstName=${ins.firstName}&lastName=${ins.lastName}&gender=${gender}`,
        "PUT"
      );
      if (result.status === "success") {
        insArr.push(ins);
      } else {
        console.log(result);
      }
    } else {
      oldInsCode = parseInt(oldInsCode);
      const idx = getIndexInstructor(oldInsCode);
      if (idx > -1) {
        const result = await AJAXMet(
          `/IE-Uni/instructor?action=update&insCode=${insCode}&firstName=${firstName}&lastName=${lastName}&gender=${gender}&oldId=${oldInsCode}`,
          "PUT"
        );
        console.log(result);
        if (result.status === "success") {
          insArr[idx].insCode = insCode;
          insArr[idx].firstName = firstName;
          insArr[idx].lastName = lastName;
          insArr[idx].gender = gender;
        } else {
          console.log(result.message);
        }
      }
    }
  }
  createInstructorList();
}

function validateInstructor(insCode, firstName, lastName, oldInsCode) {
  // Check if the required fields are not empty
  if (
    insCode.trim() === "" ||
    firstName.trim() === "" ||
    lastName.trim() === ""
  ) {
    return false;
  }

  for (let i = 0; i < insArr.length; i++) {
    if (insArr[i].insCode == insCode) {
      if (isNaN(oldInsCode)) {
        alert("There is a instructor with this Id!");
        return false;
      } else {
        const oldIndx = getIndexInstructor(oldInsCode);
        const newIndx = getIndexInstructor(insArr[i].insCode);
        if (oldIndx == newIndx) {
          continue;
        } else {
          alert("There is a instructor with this id!");
          return false;
        }
      }
    }
  }

  if (!/^\d+$/.test(insCode) || parseInt(insCode, 10) <= 0) {
    alert("Please enter a valid positive numeric value for instructor ID.");
    return false;
  }

  if (firstName.length < 2 || lastName.length < 2) {
    alert("First name and last name must be at least two characters long.");
    return false;
  }

  return true;
}

function getIndexInstructor(insCode) {
  let idx = -1;
  for (let i = 0; i < insArr.length; i++) {
    if (insArr[i].insCode == insCode) {
      idx = i;
      break;
    }
  }
  return idx;
}

function prepareEditInstructor(insCode) {
  let insIdx = getIndexInstructor(insCode);
  if (insIdx > -1) {
    let ins = insArr[insIdx];
    btnNewInstructor.disabled = true;
    inputInsCode.value = ins.insCode;
    inputInsFirstName.value = ins.firstName;
    inputInsLastName.value = ins.lastName;
    inputInsGender.value = ins.gender;
    inputInsOldId.value = ins.insCode;

    instructorForm.style.display = "block";
    inputInsCode.focus();
  }
}

async function deleteInstructor(insCode) {
  const result = await AJAXMet(`/IE-Uni/instructor?id=${insCode}`, "delete");
  if (result.status === "success") {
    const insIdx = getIndexInstructor(insCode);
    if (insIdx > -1) {
      insArr.splice(insIdx, 1);
      createInstructorList();
    }
  } else {
    console.log(result.message);
  }
}

///////////////////////////////////
//////////     Course    //////////
///////////////////////////////////

// CLASS //
class Course {
  constructor(courseId, title, unitNumber) {
    this.courseId = courseId;
    this.title = title;
    this.unitNumber = unitNumber;
  }
}
// SELECTORS //
// Btns
const btnNewCourse = document.querySelector(".btnNewCourse");
const btnCancelCourse = document.querySelector(".btnCancelCourse");
const btnSaveCourse = document.querySelector(".btnSaveCourse");

// inputs
const inputCourseId = document.getElementById("courseId");
const inputCourseTitle = document.getElementById("course-title");
const inputCourseUnits = document.getElementById("unit-number");
const inputCourseOldCode = document.getElementById("courseOldCode");

// form

const courseForm = document.getElementById("course-form");

// list

const courseList = document.getElementById("course-list");

// EVENR LISTENERS //
btnNewCourse.addEventListener("click", (e) => {
  e.preventDefault();
  initCoursetDef();
});
btnCancelCourse.addEventListener("click", initCourseElements);
btnSaveCourse.addEventListener("click", (e) => {
  e.preventDefault();
  saveCourse();
});

function initCoursetDef() {
  btnNewCourse.disabled = true;
  inputCourseId.value = "";
  inputCourseTitle.value = "";
  inputCourseUnits.value = "";
  inputCourseOldCode.value = "new";

  courseForm.style.display = "block";
  inputCourseId.focus();
}
function createCourseList() {
  initCourseElements();
  if (courseArr.length > 0) {
    createCourseTable();
  } else {
    courseList.innerHTML = "<p class='no--list'>No courses defined yet!</p>";
  }
}

function initCourseElements() {
  courseForm.style.display = "none";
  btnNewCourse.disabled = false;
}

function createCourseTable() {
  let tbl = `<table>
       <tr>
           <th>Row No</th>
           <th>Course Code</th>
           <th>Title</th>
           <th>Unit Numbers</th>
           <th></th>
           <th></th>
       </tr>`;

  for (let i = 0; i < courseArr.length; i++) {
    tbl += `<tr>
                       <td>${i + 1}</td>
                       <td>${courseArr[i].courseId}</td>
                       <td>${courseArr[i].title}</td>
                       <td>${courseArr[i].unitNumber}</td>
                       <td><button class="btn btnEdit" onclick="prepareEditCourse(${
                         courseArr[i].courseId
                       })">Edit</button></td>
                       <td><button class="btn btnDelete" onclick="deleteCourse(${
                         courseArr[i].courseId
                       })">Delete</button></td>
                       <td><button class="btn btnpres" onclick="presentCourse(${
                         courseArr[i].courseId
                       })">Present</button></td>
                   </tr>`;
  }

  tbl += "</table>";

  courseList.innerHTML = tbl;
}

async function saveCourse() {
  let courseId = inputCourseId.value;
  let title = inputCourseTitle.value;
  let unitNumber = inputCourseUnits.value;

  let oldCourseCode = inputCourseOldCode.value;
  if (validateCourse(courseId, title, unitNumber, oldCourseCode)) {
    if (isNaN(oldCourseCode)) {
      let c = new Course(courseId, title, unitNumber);
      const result = await AJAXMet(
        `/IE-Uni/course?action=save&courseId=${c.courseId}&title=${c.title}&unitNumbers=${c.unitNumber}`,
        "PUT"
      );
      if (result.status === "success") {
        courseArr.push(c);
      } else {
        console.log(result.message);
      }
    } else {
      oldCourseCode = parseInt(oldCourseCode);
      const idx = getIndexCourse(oldCourseCode);

      if (idx > -1) {
        const result = await AJAXMet(
          `/IE-Uni/course?action=update&courseId=${courseId}&title=${title}&unitNumbers=${unitNumber}&oldId=${oldCourseCode}`,
          "PUT"
        );
        console.log(result);
        if (result.status === "success") {
          courseArr[idx].courseId = courseId;
          courseArr[idx].title = title;
          courseArr[idx].unitNumber = unitNumber;
        } else {
          console.log(result.message);
        }
      }
    }
  }
  createCourseList();
}

function validateCourse(cID, cTitle, cUnits, cOldId) {
  // Check if the required fields are not empty
  if (cID.trim() === "" || cTitle.trim() === "" || cUnits.trim() === "") {
    return false;
  }

  for (let i = 0; i < courseArr.length; i++) {
    if (courseArr[i].courseId == cID) {
      if (isNaN(cOldId)) {
        alert("There is a course with this Id!");
        return false;
      } else {
        const oldIndx = getIndexCourse(cOldId);
        const newIndx = getIndexCourse(courseArr[i].courseId);
        if (oldIndx == newIndx) {
          continue;
        } else {
          alert("There is a course with this id!");
          return false;
        }
      }
    }
  }

  if (!/^\d+$/.test(cID) || parseInt(cID, 10) <= 0) {
    alert("Please enter a valid positive numeric value for course ID.");
    return false;
  }

  if (!/^\d+$/.test(cUnits) || parseInt(cUnits, 10) <= 0) {
    alert("Please enter a valid positive numeric value for course units.");
    return false;
  }

  if (cTitle.length < 2) {
    alert("Title must be at least two characters long.");
    return false;
  }

  return true;
}

function getIndexCourse(courseId) {
  let idx = -1;
  for (let i = 0; i < courseArr.length; i++) {
    if (courseArr[i].courseId == courseId) {
      idx = i;
      break;
    }
  }
  return idx;
}

function prepareEditCourse(courseId) {
  let coIdx = getIndexCourse(courseId);
  if (coIdx > -1) {
    let course = courseArr[coIdx];
    btnNewCourse.disabled = true;
    inputCourseId.value = course.courseId;
    inputCourseTitle.value = course.title;
    inputCourseUnits.value = course.unitNumber;
    inputCourseOldCode.value = course.courseId;

    courseForm.style.display = "block";
    inputCourseId.focus();
  }
}

async function deleteCourse(courseId) {
  const result = await AJAXMet(`/IE-Uni/course?id=${courseId}`, "delete");
  if (result.status === "success") {
    const coIdx = getIndexCourse(courseId);
    if (coIdx > -1) {
      courseArr.splice(coIdx, 1);
      createCourseList();
    }
  } else {
    console.log(result.message);
  }
}

///////////////////////////////////
/////  Course Presentation    /////
///////////////////////////////////

// CLASS //
class CoursePresentation {
  constructor(coursePresId, course, instructor) {
    this.coursePresId = coursePresId;
    this.course = course;
    this.instructor = instructor;
  }
}

// SELECTORS //
// Btns
const btnNewCoursePres = document.querySelector(".btnNewCoursePres");
const btnCancelCoursePres = document.querySelector(".btnCancelCoursePres");
const btnSaveCoursePres = document.querySelector(".btnSaveCoursePres");

// inputs
const inputCoursePresId = document.getElementById("coursePresId");
// const inputCourseTitle = document.getElementById("course-title");
// const inputCourseUnits = document.getElementById("unit-number");
const inputCoursePresOldCode = document.getElementById("coursePresOldCode");

// form

const coursePresForm = document.getElementById("coursePres-form");

// list

const coursePresList = document.getElementById("course_pres-list");

const btnCancelAllStudents = document.querySelector(".btnCancelAllStudents");

// EVENR LISTENERS //
btnNewCoursePres.addEventListener("click", (e) => {
  e.preventDefault();
  if (courseArr.length > 0 && insArr.length > 0) {
    document.querySelector(".noCourse").innerHTML = "";
    initCoursePresDef();
  } else {
    document.querySelector(".noCourse").innerHTML =
      "<p class='no--list'>There is no course or instructor! Add them first.</p>";
  }
});

btnCancelAllStudents.addEventListener("click", (e) => {
  e.preventDefault();
  document.querySelector(".modal_Allstudent").classList.add("hidden");
});

btnCancelCoursePres.addEventListener("click", initCoursePresElements);
btnSaveCoursePres.addEventListener("click", (e) => {
  e.preventDefault();
  saveCoursePres();
});

function initCoursePresDef() {
  btnNewCoursePres.disabled = true;
  inputCoursePresId.value = "";
  inputCoursePresOldCode.value = "new";

  coursePresForm.style.display = "block";
  generateAllCourses();
  generateAllInstructors();
  inputCoursePresId.focus();
}

function createCoursePresList() {
  initCoursePresElements();
  if (coursePresArr.length > 0) {
    createCoursesPresTable();
  } else {
    coursePresList.innerHTML =
      "<p class='no--list'>No courses presented yet! Click to present new one!</p>";
  }
}

function initCoursePresElements() {
  coursePresForm.style.display = "none";
  btnNewCoursePres.disabled = false;
}

function createCoursesPresTable() {
  let tbl = `<div class="course_pres-list"><table>
       <tr>
           <th>Row No</th>
           <th>Presentation Code</th>
           <th>Course code</th>
           <th>Course Title</th>
           <th>Unit Numbers</th>
           <th>Instructor</th>
           <th></th>
           <th></th>
       </tr>`;

  for (let i = 0; i < coursePresArr.length; i++) {
    tbl += `<tr>
                       <td>${i + 1}</td>
                       <td>${coursePresArr[i].coursePresId}</td>
                       <td>${coursePresArr[i].course.courseId}</td>
                       <td>${coursePresArr[i].course.title}</td>
                       <td>${coursePresArr[i].course.unitNumber}</td>
                       <td>${coursePresArr[i].instructor.getFullName()}</td>
                       <td><button class="btn btnEdit" onclick="prepareEditCoursePres(${
                         coursePresArr[i].coursePresId
                       })">Edit</button></td>
                       <td><button class="btn btnDelete" onclick="deleteCoursePres(${
                         coursePresArr[i].coursePresId
                       })">Delete</button></td>
                       <td><button class="btn btnAllStudents" onclick="showCoursePresStudents(${
                         coursePresArr[i].coursePresId
                       })">See Students</button></td>
                   </tr>`;
  }

  tbl += "</table></div>";

  coursePresList.innerHTML = tbl;
}

function generateAllCourses() {
  const allCourses = document.querySelector(".all_courses");
  let tbl = `<div><h2 class="all_header">Select the course that you want to present</h2></div>
        <table>
       <tr>
           <th></th>
           <th>Row No</th>
           <th>Course Code</th>
           <th>Title</th>
           <th>Unit Numbers</th>
       </tr>`;

  for (let i = 0; i < courseArr.length; i++) {
    tbl += `<tr>
                      <td><input name="coursechecked" type="radio" value="${
                        courseArr[i].courseId
                      }"/>
                       <td>${i + 1}</td>
                       <td>${courseArr[i].courseId}</td>
                       <td>${courseArr[i].title}</td>
                       <td>${courseArr[i].unitNumber}</td>
                   </tr>`;
  }

  tbl += "</table>";
  allCourses.innerHTML = tbl;
}

function showCoursePresStudents(corsePresId) {
  document.querySelector(".modal_Allstudent").classList.remove("hidden");
  const stduentsOfCourse = document.querySelector(".stduentsOfCourse");

  const studentArr = [];
  const garades = [];

  courseSelArr.map((e) => {
    // Push which element has same corsePresId to studentArr
    if (e.coursePres.coursePresId == corsePresId) {
      studentArr.push(e.student);
      garades.push(e.grade);
    }
  });

  if (studentArr.length == 0) {
    stduentsOfCourse.innerHTML = `<p class="no--list">There is no student selected this course!</p>`;
  } else {
    let tbl = `
        <table>
       <tr>
           <th>Row No</th>
           <th>Student Code</th>
           <th>Name</th>
           <th>Gender</th>
           <th>Grade</th>
       </tr>`;

    for (let i = 0; i < studentArr.length; i++) {
      tbl += `<tr>
                         <td>${i + 1}</td>
                         <td>${studentArr[i].stCode}</td>
                         <td>${studentArr[i].getFullName()}</td>
                         <td>${studentArr[i].getGenderTitle()}</td>
                         <td>${garades[i]}</td>
                     </tr>`;
    }
    tbl += "</table>";
    tbl += `<div><button class="btn btnGrade" onclick="GradeCoursePresStudents(${corsePresId})">Grade</button></div>`;
    stduentsOfCourse.innerHTML = tbl;
  }
}

function GradeCoursePresStudents(corsePresId) {
  const stduentsOfCourse = document.querySelector(".stduentsOfCourse");

  const studentArr = [];

  courseSelArr.map((e) => {
    // Push which element has same corsePresId to studentArr
    if (e.coursePres.coursePresId == corsePresId) {
      studentArr.push(e.student);
    }
  });

  let tbl = `
        <table>
       <tr>
           <th>Row No</th>
           <th>Student Code</th>
           <th>Name</th>
           <th>Gender</th>
           <th>Grade</th>
       </tr>`;

  for (let i = 0; i < studentArr.length; i++) {
    tbl += `<tr>
                         <td>${i + 1}</td>
                         <td>${studentArr[i].stCode}</td>
                         <td>${studentArr[i].getFullName()}</td>
                         <td>${studentArr[i].getGenderTitle()}</td>
                         <td><input type="text" class="gradeInput" data-id="${
                           studentArr[i].stCode
                         }" required/>
                     </tr>`;
  }
  tbl += "</table>";
  tbl += `<div>
          <button class="btn btnSave" onclick="saveGrades(${corsePresId})">Save</button>
          <button class="btn btnCancel" onclick="showCoursePresStudents(${corsePresId})">Cancel</button>
                </div>`;
  stduentsOfCourse.innerHTML = tbl;
}

async function saveGrades(corsePresId) {
  const inputs = document.querySelectorAll(".gradeInput");
  console.log(inputs);
  for (let i = 0; i < inputs.length; i++) {
    for (let j = 0; j < courseSelArr.length; j++) {
      if (
        inputs[i].getAttribute("data-id") == courseSelArr[j].student.stCode &&
        corsePresId == courseSelArr[j].coursePres.coursePresId
      ) {
        if (inputs[i].value >= 0 && inputs[i].value <= 20) {
          const course = courseSelArr[j];
          const result = await AJAX(
            `/IE-Uni/course/grade?grade=${inputs[i].value}&courseSelId=${course.courseSelId}`
          );
          if (result.status === "success") {
            courseSelArr[j].setGrade(inputs[i].value);
          } else {
            console.log(result.message);
          }
        }
      }
    }
  }
  showCoursePresStudents(corsePresId);
}

function generateAllInstructors() {
  const allInstructors = document.querySelector(".all_instructors");
  let tbl = `<div><h2 class="all_header">Select the instructor</h2></div>
        <table>
       <tr>
           <th></th>
           <th>Name</th>
       </tr>`;

  for (let i = 0; i < insArr.length; i++) {
    tbl += `<tr>
                      <td><input name="instructorchechked" type="radio" value="${
                        insArr[i].insCode
                      }"/>
                       <td>${insArr[i].getFullName()}</td>
                   </tr>`;
  }

  tbl += "</table>";
  allInstructors.innerHTML = tbl;
}

async function saveCoursePres() {
  let coursePresId = inputCoursePresId.value;
  let oldCoursePresCode = inputCoursePresOldCode.value;
  const courseEl = document.getElementsByName("coursechecked");
  const insEl = document.getElementsByName("instructorchechked");
  let courseId, instructorId;
  for (let i = 0; i < courseEl.length; i++) {
    if (courseEl[i].checked) {
      courseId = courseEl[i].value;
    }
  }

  for (let i = 0; i < insEl.length; i++) {
    if (insEl[i].checked) {
      instructorId = insEl[i].value;
    }
  }
  console.log(courseId, instructorId);

  if (
    validateCoursePres(coursePresId, courseId, instructorId, oldCoursePresCode)
  ) {
    if (isNaN(oldCoursePresCode)) {
      let c = new CoursePresentation(
        coursePresId,
        courseArr[getIndexCourse(courseId)],
        insArr[getIndexInstructor(instructorId)]
      );
      const result = await AJAXMet(
        `/IE-Uni/coursepres?action=save&courseId=${c.course.courseId}&coursePresId=${c.coursePresId}&insCode=${c.instructor.insCode}`,
        "PUT"
      );
      if (result.status === "success") {
        coursePresArr.push(c);
      } else {
        console.log(result.message);
      }
    } else {
      /// continue for edit
      oldCoursePresCode = parseInt(oldCoursePresCode);
      const idx = getIndexCoursePres(oldCoursePresCode);
      if (idx > -1) {
        const result = await AJAXMet(
          `/IE-Uni/coursepres?action=update&courseId=${courseId}&coursePresId=${coursePresId}&insCode=${instructorId}&oldId=${oldCoursePresCode}`,
          "PUT"
        );
        if (result.status === "success") {
          coursePresArr[idx].coursePresId = coursePresId;
          coursePresArr[idx].course = courseArr[getIndexCourse(courseId)];
          coursePresArr[idx].instructor =
            insArr[getIndexInstructor(instructorId)];
        } else {
          console.log(result.message);
        }
      }
    }
  }
  createCoursePresList();
}
function prepareEditCoursePres(coursePresId) {
  let cpIdx = getIndexCoursePres(coursePresId);
  if (cpIdx > -1) {
    let course = coursePresArr[cpIdx];
    btnNewCoursePres.disabled = true;
    inputCoursePresId.value = course.coursePresId;
    inputCoursePresOldCode.value = course.coursePresId;

    coursePresForm.style.display = "block";
    inputCoursePresId.focus();
  }
}

function validateCoursePres(cpID, courseId, instructorId, cpOldId) {
  for (let i = 0; i < coursePresArr.length; i++) {
    if (coursePresArr[i].coursePresId == cpID) {
      if (isNaN(cpOldId)) {
        alert("A course presented with this Id!");
        return false;
      } else {
        const oldIndx = getIndexCoursePres(cpOldId);
        const newIndx = getIndexCoursePres(coursePresArr[i].coursePresId);
        if (oldIndx == newIndx) {
          continue;
        } else {
          alert("A course presented with this Id!");
          return false;
        }
      }
    }
  }

  if (!/^\d+$/.test(cpID) || parseInt(cpID, 10) <= 0) {
    alert("Please enter a valid positive numeric value for presentation ID.");
    return false;
  }
  if (getIndexCourse(courseId) < 0 || getIndexInstructor(instructorId) < 0) {
    alert("No course or instructor that you select! maybe you delete it!");
    return false;
  }

  return true;
}

function getIndexCoursePres(coursePresId) {
  let idx = -1;
  for (let i = 0; i < coursePresArr.length; i++) {
    if (coursePresArr[i].coursePresId == coursePresId) {
      idx = i;
      break;
    }
  }
  return idx;
}

async function deleteCoursePres(coursePresId) {
  const result = await AJAXMet(
    `/IE-Uni/coursepres?id=${coursePresId}`,
    "delete"
  );
  if (result.status === "success") {
    const coIdx = getIndexCoursePres(coursePresId);
    if (coIdx > -1) {
      coursePresArr.splice(coIdx, 1);
      createCoursePresList();
    }
  } else {
    console.log(result.message);
  }
}

///////////////////////////////////
/////     Courde Selected   ///////
///////////////////////////////////

// CLASS //
class CourseSelected {
  static id = 0;
  constructor(student, coursePres) {
    this.courseSelId = CourseSelected.setId();
    this.student = student;
    this.coursePres = coursePres;
    this.grade = "Undefined";
  }
  static setId() {
    return ++CourseSelected.id;
  }
  setGrade(grade) {
    this.grade = grade;
  }
}

const btnNewCourseSel = document.querySelector(".btnNewCourseSel");

const selectedCoursesList = document.getElementById("selectedCoursesList");

const coursesCanSelectList = document.querySelector(".coursesCanSelectList");

const btnCancelSelCourses = document.querySelector(".btnCancelSelCourses");

btnNewCourseSel.addEventListener("click", (e) => {
  e.preventDefault();
  btnNewCourseSel.disabled = true;
  showOfferedCoursesList();
});

btnCancelSelCourses.addEventListener("click", (e) => {
  e.preventDefault();
  document.querySelector(".coursesSelected").classList.add("hidden");
});

function showSelectedCourses(stCode) {
  document.querySelector(".coursesSelected").classList.remove("hidden");
  btnNewCourseSel.disabled = false;
  coursesCanSelectList.innerHTML = "";
  let student;
  for (let i = 0; i < stArr.length; i++) {
    if (stArr[i].stCode == stCode) {
      student = stArr[i];
    }
  }
  let grade = [];
  for (let i = 0; i < courseSelArr.length; i++) {
    for (let j = 0; j < student.selectedCourses.length; j++) {
      if (
        courseSelArr[i].coursePres.coursePresId ==
          student.selectedCourses[j].coursePresId &&
        courseSelArr[i].student.stCode == student.stCode
      ) {
        grade.push(courseSelArr[i].grade);
      }
    }
  }
  if (student.selectedCourses.length === 0) {
    selectedCoursesList.innerHTML = `<p class="no--list">No Courses selected yet!</p>
            <input type="hidden" class="currentStudent" value=${stCode} />`;
  } else {
    let tbl = `<table>
                      <tr>
                        <th>Row No</th>
                        <th>Course Code</th>
                        <th>Presentation Code</th>
                        <th>Title</th>
                        <th>Unit Numbers</th>
                        <th>Teacher</th>
                        <th>Grade</th>
                      </tr>`;

    for (let i = 0; i < student.selectedCourses.length; i++) {
      tbl += `<tr>
                       <td>${i + 1}</td>
                       <td>${student.selectedCourses[i].course.courseId}</td>
                       <td>${student.selectedCourses[i].coursePresId}</td>
                       <td>${student.selectedCourses[i].course.title}</td>
                       <td>${student.selectedCourses[i].course.unitNumber}</td>
                       <td>${student.selectedCourses[
                         i
                       ].instructor.getFullName()}</td>
                       <td>${grade[i]}</td>
                       <td><button class="btn btnDelete" onclick="deleteCourseSelected(${
                         student.selectedCourses[i].coursePresId
                       }, ${student.stCode})">Delete</button></td>
                   </tr>`;
    }

    tbl += "</table>";
    tbl += `<input type="hidden" class="currentStudent" value=${stCode} />`;
    selectedCoursesList.innerHTML = tbl;
  }
}
function showOfferedCoursesList() {
  if (coursePresArr.length === 0) {
    coursesCanSelectList.innerHTML = `<p class="no--list">No Courses Offered yet! Go ahed and present some courses.</p>`;
  } else {
    let tbl = `<table>
                    <tr>
                      <th></th>
                      <th>Row No</th>
                      <th>Presentation Code</th>
                      <th>Course code</th>
                      <th>Course Title</th>
                      <th>Unit Numbers</th>
                      <th>Instructor</th>
                    </tr>`;

    for (let i = 0; i < coursePresArr.length; i++) {
      tbl += `<tr>
                      <td><input class="coursesToSelect"  type="radio" value="${
                        coursePresArr[i].coursePresId
                      }"/>
                      <td>${i + 1}</td>
                      <td>${coursePresArr[i].coursePresId}</td>
                      <td>${coursePresArr[i].course.courseId}</td>
                       <td>${coursePresArr[i].course.title}</td>
                       <td>${coursePresArr[i].course.unitNumber}</td>
                       <td>${coursePresArr[i].instructor.getFullName()}</td>
                   </tr>`;
    }

    tbl += "</table>";
    tbl += `<div class="flex-tocenter"><div class="studentAddCoursesBtns"><button class="btn btnAddSelectedCourses" id="btnAddSelectedCourses" onclick="saveSelectedCourses()">Add Courses</button>
          <button class="btn btnCanselAddSelectedCourses" id="btnCanselAddSelectedCourses" onclick="CancelSelectedCourses()">Cancel</button></div></div>`;

    coursesCanSelectList.innerHTML = tbl;
  }
}

async function saveSelectedCourses() {
  const allSelectedCourses = document.querySelectorAll(".coursesToSelect");
  const stCode = document.querySelector(".currentStudent").value;
  const st = stArr[getIndexStudent(stCode)];

  for (let i = 0; i < allSelectedCourses.length; i++) {
    if (allSelectedCourses[i].checked) {
      const coursePresCode = allSelectedCourses[i].value;
      for (let i = 0; i < courseSelArr.length; i++) {
        if (
          courseSelArr[i].student.stCode == stCode &&
          courseSelArr[i].coursePres.coursePresId == coursePresCode
        ) {
          alert(
            `The course ${
              coursePresArr[getIndexCoursePres(coursePresCode)].course.title
            } has already selected!`
          );
          return;
        }
      }
      const courseSelected = new CourseSelected(
        st,
        coursePresArr[getIndexCoursePres(coursePresCode)]
      );
      const result = await AJAXMet(
        `/IE-Uni/course/select?stCode=${st.stCode}&cCode=${coursePresCode}&action=save`,
        "PUT"
      );
      if (result.status === "success") {
        courseSelArr.push(courseSelected);
        st.selectedCourses.push(
          coursePresArr[getIndexCoursePres(coursePresCode)]
        );
      } else {
        console.log(result.message);
      }
    }
  }
  coursesCanSelectList.innerHTML = "";
  btnNewCourseSel.disabled = false;
  showSelectedCourses(stCode);
}

function CancelSelectedCourses() {
  coursesCanSelectList.innerHTML = "";
  btnNewCourseSel.disabled = false;
}

async function deleteCourseSelected(coursePresId, stCode) {
  console.log(coursePresId);
  const stIdx = getIndexStudent(stCode);
  if (stIdx > -1) {
    const result = await AJAXMet(
      `/IE-Uni/course/select?stCode=${stCode}&cCode=${coursePresId}`,
      "delete"
    );
    if (result.status === "success") {
      stArr[stIdx].selectedCourses.splice(
        getIndexCourseSel(coursePresId, stIdx),
        1
      );
      showSelectedCourses(stCode);
      for (let i = 0; i < courseSelArr.length; i++) {
        if (
          courseSelArr[i].coursePres.coursePresId == coursePresId &&
          courseSelArr[i].student.stCode == stCode
        ) {
          courseSelArr.splice(i, 1);
        }
      }
    }
  }
}
const PER_PAGE = 10;
function getIndexCourseSel(coursePresId, stIdx) {
  let idx = -1;
  for (let i = 0; i < stArr[stIdx].selectedCourses.length; i++) {
    if (stArr[stIdx].selectedCourses[i].coursePresId == coursePresId) {
      // error
      idx = i;
      break;
    }
  }
  return idx;
}

//////////////////////////////
////   GENERAL FUNCTIONS  ////
