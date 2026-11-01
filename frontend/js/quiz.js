const API_BASE_URL = "http://localhost:8080";

let quiz = null;
let questions = [];

let currentQuestionIndex = 0;
let selectedOption = null;

let timerInterval = null;
let timeLeft = 30;

let score = 0;
let answeredQuestions = 0;


/* =========================================================
   START
   ========================================================= */

document.addEventListener("DOMContentLoaded", function () {

    loadQuiz();

});


/* =========================================================
   GET QUIZ ID
   ========================================================= */

function getQuizId() {

    const params =
        new URLSearchParams(window.location.search);

    return params.get("quizId");
}


/* =========================================================
   LOAD QUIZ
   ========================================================= */

async function loadQuiz() {

    const quizId = getQuizId();

    if (!quizId) {

        showError("Quiz ID is missing.");

        return;
    }

    try {

        const response =
            await fetch(
                API_BASE_URL +
                "/api/quizzes/" +
                quizId
            );

        if (!response.ok) {

            showError("Unable to load quiz.");

            return;
        }

        quiz = await response.json();

        questions =
            Array.isArray(quiz.questions)
                ? quiz.questions
                : [];

        if (questions.length === 0) {

            showError(
                "This quiz does not contain any questions."
            );

            return;
        }

        showQuestion();

    } catch (error) {

        console.error(
            "CIPHERX Quiz Error:",
            error
        );

        showError(
            "Backend connection failed."
        );
    }
}


/* =========================================================
   SHOW QUESTION
   ========================================================= */

function showQuestion() {

    clearInterval(timerInterval);

    selectedOption = null;

    const question =
        questions[currentQuestionIndex];

    const totalQuestions =
        questions.length;


    /* QUESTION NUMBER */

    const questionNumber =
        document.getElementById(
            "questionNumber"
        );

    questionNumber.textContent =
        String(
            currentQuestionIndex + 1
        ).padStart(2, "0");


    /* QUESTION TEXT */

    document.getElementById(
        "questionText"
    ).textContent =
        question.questionText ||
        "Question unavailable.";


    /* PROGRESS */

    const progress =
        ((currentQuestionIndex + 1) /
            totalQuestions) * 100;

    document.getElementById(
        "progressText"
    ).textContent =
        `Question ${currentQuestionIndex + 1} of ${totalQuestions}`;

    document.getElementById(
        "progressPercent"
    ).textContent =
        `${Math.round(progress)}%`;

    document.getElementById(
        "progressBar"
    ).style.width =
        progress + "%";


    /* OPTIONS */

    renderOptions(question);


    /* BUTTON */

    const nextButton =
        document.getElementById(
            "nextButton"
        );

    nextButton.disabled = true;

    document.getElementById(
        "nextButtonText"
    ).textContent =
        currentQuestionIndex ===
        totalQuestions - 1
            ? "SUBMIT"
            : "NEXT";


    /* STATUS */

    const selectionStatus =
        document.getElementById(
            "selectionStatus"
        );

    selectionStatus.textContent =
        "Select an answer to continue";

    selectionStatus.classList.remove(
        "selected-text"
    );


    /* TIMER */

    timeLeft =
        quiz.timePerQuestion ||
        30;

    startTimer();

}


/* =========================================================
   RENDER OPTIONS
   ========================================================= */

function renderOptions(question) {

    const container =
        document.getElementById(
            "optionsContainer"
        );

    container.innerHTML = "";

    const options =
        Array.isArray(question.options)
            ? question.options
            : [];

    const letters =
        ["A", "B", "C", "D", "E", "F"];

    options.forEach(
        function (option, index) {

            const button =
                document.createElement(
                    "button"
                );

            button.type = "button";

            button.className =
                "option-card";

            button.innerHTML = `
                <span class="option-letter">
                    ${letters[index] || index + 1}
                </span>

                <span class="option-text">
                    ${escapeHtml(option)}
                </span>
            `;

            button.addEventListener(
                "click",
                function () {

                    selectOption(
                        index,
                        button
                    );

                }
            );

            container.appendChild(
                button
            );

        }
    );
}


/* =========================================================
   SELECT OPTION
   ========================================================= */

function selectOption(
    optionIndex,
    selectedButton
) {

    selectedOption = optionIndex;


    document
        .querySelectorAll(
            ".option-card"
        )
        .forEach(
            function (button) {

                button.classList.remove(
                    "selected"
                );

            }
        );


    selectedButton.classList.add(
        "selected"
    );


    const nextButton =
        document.getElementById(
            "nextButton"
        );

    nextButton.disabled = false;


    const selectionStatus =
        document.getElementById(
            "selectionStatus"
        );

    selectionStatus.textContent =
        "Answer selected";

    selectionStatus.classList.add(
        "selected-text"
    );
}


/* =========================================================
   NEXT BUTTON
   ========================================================= */

document
    .getElementById("nextButton")
    .addEventListener(
        "click",
        function () {

            if (selectedOption === null) {
                return;
            }

            processAnswer();

        }
    );


/* =========================================================
   PROCESS ANSWER
   ========================================================= */

function processAnswer() {

    clearInterval(timerInterval);

    const question =
        questions[currentQuestionIndex];


    /*
     * The backend currently sends correctOption
     * with the Question object.
     */

    if (
        typeof question.correctOption ===
        "number" &&
        selectedOption ===
        question.correctOption
    ) {

        score +=
            question.points || 0;

    }


    answeredQuestions++;


    if (
        currentQuestionIndex >=
        questions.length - 1
    ) {

        finishQuiz();

        return;
    }


    currentQuestionIndex++;

    showQuestion();
}


/* =========================================================
   TIMER
   ========================================================= */

function startTimer() {

    updateTimerDisplay();

    timerInterval =
        setInterval(
            function () {

                timeLeft--;

                updateTimerDisplay();


                if (timeLeft <= 0) {

                    clearInterval(
                        timerInterval
                    );

                    handleTimeExpired();

                }

            },
            1000
        );
}


/* =========================================================
   TIMER DISPLAY
   ========================================================= */

function updateTimerDisplay() {

    const timer =
        document.getElementById(
            "timer"
        );

    const minutes =
        Math.floor(
            timeLeft / 60
        );

    const seconds =
        timeLeft % 60;


    timer.textContent =
        String(minutes).padStart(2, "0") +
        ":" +
        String(seconds).padStart(2, "0");


    timer.classList.remove(
        "warning",
        "danger"
    );


    if (timeLeft <= 10) {

        timer.classList.add(
            "danger"
        );

    } else if (timeLeft <= 20) {

        timer.classList.add(
            "warning"
        );
    }
}


/* =========================================================
   TIME EXPIRED
   ========================================================= */

function handleTimeExpired() {

    selectedOption = null;

    answeredQuestions++;


    if (
        currentQuestionIndex >=
        questions.length - 1
    ) {

        finishQuiz();

        return;
    }


    currentQuestionIndex++;

    showQuestion();
}


/* =========================================================
   FINISH QUIZ
   ========================================================= */

function finishQuiz() {

    clearInterval(timerInterval);

    const overlay =
        document.getElementById(
            "quizOverlay"
        );

    const resultScore =
        document.getElementById(
            "resultScore"
        );

    const totalQuestions =
        questions.length;


    resultScore.textContent =
        `${score} points`;


    document.getElementById(
        "resultMessage"
    ).textContent =
        `You completed ${answeredQuestions} of ${totalQuestions} questions.`;


    overlay.classList.add(
        "show"
    );


    sessionStorage.setItem(
        "cipherxLastScore",
        score
    );

    sessionStorage.setItem(
        "cipherxTotalQuestions",
        totalQuestions
    );
}


/* =========================================================
   ERROR
   ========================================================= */

function showError(message) {

    const questionText =
        document.getElementById(
            "questionText"
        );

    if (questionText) {

        questionText.textContent =
            message;
    }

    const options =
        document.getElementById(
            "optionsContainer"
        );

    if (options) {

        options.innerHTML = "";

    }

}


/* =========================================================
   HTML ESCAPE
   ========================================================= */

function escapeHtml(value) {

    return String(value)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}