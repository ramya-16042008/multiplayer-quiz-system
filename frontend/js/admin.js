const API_BASE_URL = "http://localhost:8080";


/* =========================================================
   AUTHENTICATION
========================================================= */

function getAuthHeaders() {

    const token =
        sessionStorage.getItem("cipherxToken");

    const headers = {
        "Content-Type": "application/json"
    };

    if (token) {
        headers["Authorization"] =
            "Bearer " + token;
    }

    return headers;
}


/* =========================================================
   CREATE QUIZ
========================================================= */

const createQuizForm =
    document.getElementById("createQuizForm");

if (createQuizForm) {

    createQuizForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();

            const title =
                document
                    .getElementById("quizTitle")
                    .value
                    .trim();

            const description =
                document
                    .getElementById("quizDescription")
                    .value
                    .trim();

            const timePerQuestion =
                Number(
                    document
                        .getElementById("timePerQuestion")
                        .value
                );

            const entryPassword =
                document
                    .getElementById("entryPassword")
                    .value;

            const message =
                document.getElementById(
                    "createQuizMessage"
                );


            /* -------------------------
               VALIDATION
            ------------------------- */

            if (!title || !entryPassword) {

                message.textContent =
                    "Please fill in all required fields.";

                return;
            }

            if (timePerQuestion < 5) {

                message.textContent =
                    "Time per question must be at least 5 seconds.";

                return;
            }


            /* -------------------------
               CHECK LOGIN SESSION
            ------------------------- */

            const token =
                sessionStorage.getItem(
                    "cipherxToken"
                );

            if (!token) {

                message.textContent =
                    "Admin session expired. Please login again.";

                setTimeout(function () {

                    window.location.href =
                        "login.html";

                }, 1000);

                return;
            }


            message.textContent =
                "Creating quiz...";


            /* -------------------------
               GENERATE QUIZ CODE
            ------------------------- */

            const quizCode =
                "CX-" +
                Math.random()
                    .toString(36)
                    .substring(2, 8)
                    .toUpperCase();


            /* -------------------------
               QUIZ DATA
            ------------------------- */

            const quizData = {

                title: title,

                description: description,

                quizCode: quizCode,

                entryPassword: entryPassword,

                timePerQuestion:
                    timePerQuestion,

                active: false

            };


            /* -------------------------
               SEND TO BACKEND
            ------------------------- */

            try {

                const response =
                    await fetch(
                        API_BASE_URL +
                        "/api/quizzes",
                        {
                            method: "POST",

                            headers:
                                getAuthHeaders(),

                            body:
                                JSON.stringify(
                                    quizData
                                )
                        }
                    );


                /* -------------------------
                   UNAUTHORIZED
                ------------------------- */

                if (response.status === 401) {

                    message.textContent =
                        "Admin session expired. Please login again.";

                    sessionStorage.removeItem(
                        "cipherxToken"
                    );

                    sessionStorage.removeItem(
                        "cipherxAuthenticated"
                    );

                    setTimeout(function () {

                        window.location.href =
                            "login.html";

                    }, 1000);

                    return;
                }


                /* -------------------------
                   OTHER ERRORS
                ------------------------- */

                if (!response.ok) {

                    const errorText =
                        await response.text();

                    console.error(
                        "Quiz creation error:",
                        errorText
                    );

                    message.textContent =
                        "Failed to create quiz.";

                    return;
                }


                /* -------------------------
                   SUCCESS
                ------------------------- */

                const savedQuiz =
                    await response.json();


                console.log(
                    "CIPHERX Quiz Created:",
                    savedQuiz
                );


                /* -------------------------
                   SAVE QUIZ SESSION
                ------------------------- */

                sessionStorage.setItem(
                    "cipherxCurrentQuiz",
                    JSON.stringify(
                        savedQuiz
                    )
                );

                sessionStorage.setItem(
                    "cipherxQuizId",
                    savedQuiz.quizId
                );

                sessionStorage.setItem(
                    "cipherxQuizCode",
                    savedQuiz.quizCode
                );


                message.textContent =
                    "Quiz created successfully. Opening question manager...";


                /* -------------------------
                   OPEN QUESTIONS
                ------------------------- */

                setTimeout(
                    function () {

                        window.location.href =
                            "questions.html?quizId=" +
                            savedQuiz.quizId;

                    },
                    700
                );

            } catch (error) {

                console.error(
                    "CIPHERX Backend Error:",
                    error
                );

                message.textContent =
                    "Backend connection failed. Make sure CIPHERX server is running.";
            }

        }
    );
}


/* =========================================================
   QUESTION MODAL
========================================================= */

const questionModal =
    document.getElementById(
        "questionModal"
    );

const addQuestionBtn =
    document.getElementById(
        "addQuestionBtn"
    );

const emptyAddQuestionBtn =
    document.getElementById(
        "emptyAddQuestionBtn"
    );

const closeQuestionModal =
    document.getElementById(
        "closeQuestionModal"
    );

const cancelQuestionBtn =
    document.getElementById(
        "cancelQuestionBtn"
    );

const questionForm =
    document.getElementById(
        "questionForm"
    );

const questionsList =
    document.getElementById(
        "questionsList"
    );


/* =========================================================
   OPEN QUESTION MODAL
========================================================= */

function openQuestionModal() {

    if (questionModal) {

        questionModal.classList.remove(
            "hidden"
        );
    }
}


/* =========================================================
   CLOSE QUESTION MODAL
========================================================= */

function closeQuestionModalWindow() {

    if (questionModal) {

        questionModal.classList.add(
            "hidden"
        );
    }
}


/* =========================================================
   MODAL BUTTON EVENTS
========================================================= */

if (addQuestionBtn) {

    addQuestionBtn.addEventListener(
        "click",
        openQuestionModal
    );
}


if (emptyAddQuestionBtn) {

    emptyAddQuestionBtn.addEventListener(
        "click",
        openQuestionModal
    );
}


if (closeQuestionModal) {

    closeQuestionModal.addEventListener(
        "click",
        closeQuestionModalWindow
    );
}


if (cancelQuestionBtn) {

    cancelQuestionBtn.addEventListener(
        "click",
        closeQuestionModalWindow
    );
}


/* =========================================================
   LOAD SAVED QUESTIONS
========================================================= */

let questions = JSON.parse(
    sessionStorage.getItem(
        "cipherxCurrentQuestions"
    ) || "[]"
);


/* =========================================================
   ADD QUESTION
========================================================= */

if (questionForm) {

    questionForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            const questionText =
                document
                    .getElementById(
                        "questionTextInput"
                    )
                    .value
                    .trim();


            const options = [

                document
                    .getElementById(
                        "optionA"
                    )
                    .value
                    .trim(),

                document
                    .getElementById(
                        "optionB"
                    )
                    .value
                    .trim(),

                document
                    .getElementById(
                        "optionC"
                    )
                    .value
                    .trim(),

                document
                    .getElementById(
                        "optionD"
                    )
                    .value
                    .trim()

            ];


            const correctOption =
                Number(
                    document
                        .getElementById(
                            "correctOption"
                        )
                        .value
                );


            const points =
                Number(
                    document
                        .getElementById(
                            "questionPoints"
                        )
                        .value
                );


            /* -------------------------
               VALIDATION
            ------------------------- */

            if (!questionText) {

                alert(
                    "Please enter the question."
                );

                return;
            }


            if (
                options.some(
                    option => !option
                )
            ) {

                alert(
                    "Please fill all four options."
                );

                return;
            }


            if (points < 1) {

                alert(
                    "Points must be at least 1."
                );

                return;
            }


            /* -------------------------
               QUESTION OBJECT
            ------------------------- */

            const question = {

                questionText:
                    questionText,

                options:
                    options,

                correctOption:
                    correctOption,

                points:
                    points

            };


            /* -------------------------
               TEMPORARY FRONTEND STORAGE
            ------------------------- */

            questions.push(
                question
            );


            sessionStorage.setItem(
                "cipherxCurrentQuestions",
                JSON.stringify(
                    questions
                )
            );


            /* -------------------------
               RESET FORM
            ------------------------- */

            questionForm.reset();


            closeQuestionModalWindow();


            renderQuestions();

        }
    );
}


/* =========================================================
   RENDER QUESTIONS
========================================================= */

function renderQuestions() {

    if (!questionsList) {
        return;
    }


    /* -------------------------
       EMPTY STATE
    ------------------------- */

    if (questions.length === 0) {

        questionsList.innerHTML = `

            <div class="empty-state">

                <h3>
                    No questions added
                </h3>

                <p>
                    Start building your quiz
                    by adding your first question.
                </p>

                <button
                    id="emptyAddQuestionBtn"
                    class="btn primary">

                    Add Question

                </button>

            </div>

        `;


        const newEmptyButton =
            document.getElementById(
                "emptyAddQuestionBtn"
            );


        if (newEmptyButton) {

            newEmptyButton.addEventListener(
                "click",
                openQuestionModal
            );
        }


        return;
    }


    /* -------------------------
       QUESTION LIST
    ------------------------- */

    questionsList.innerHTML = "";


    questions.forEach(
        function (
            question,
            index
        ) {

            const item =
                document.createElement(
                    "div"
                );


            item.className =
                "question-item";


            item.innerHTML = `

                <p class="eyebrow">

                    QUESTION ${index + 1}

                </p>


                <h3>

                    ${escapeHTML(
                        question.questionText
                    )}

                </h3>


                <div class="question-options">

                    ${question.options
                        .map(
                            function (
                                option,
                                optionIndex
                            ) {

                                return `

                                    <div
                                        class="
                                            question-option
                                            ${
                                                optionIndex ===
                                                question.correctOption
                                                    ? "correct"
                                                    : ""
                                            }
                                        "
                                    >

                                        ${String.fromCharCode(
                                            65 +
                                            optionIndex
                                        )}.

                                        ${escapeHTML(
                                            option
                                        )}

                                    </div>

                                `;

                            }
                        )
                        .join("")
                    }

                </div>


                <p
                    style="
                        margin-top: 15px;
                        color: #727b91;
                    "
                >

                    Points:
                    ${question.points}

                </p>

            `;


            questionsList.appendChild(
                item
            );

        }
    );
}


/* =========================================================
   ESCAPE HTML
========================================================= */

function escapeHTML(value) {

    return String(value)

        .replaceAll(
            "&",
            "&amp;"
        )

        .replaceAll(
            "<",
            "&lt;"
        )

        .replaceAll(
            ">",
            "&gt;"
        )

        .replaceAll(
            '"',
            "&quot;"
        )

        .replaceAll(
            "'",
            "&#039;"
        );
}


/* =========================================================
   INITIAL RENDER
========================================================= */

if (questionsList) {

    renderQuestions();

}