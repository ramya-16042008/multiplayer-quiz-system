const API_BASE_URL = "http://localhost:8080";


/* =========================================================
   PLAYER JOIN
   ========================================================= */

const joinQuizForm =
    document.getElementById("joinQuizForm");

if (joinQuizForm) {

    joinQuizForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();

            const playerName =
                document
                    .getElementById("playerName")
                    .value
                    .trim();

            const quizId =
                document
                    .getElementById("quizId")
                    .value
                    .trim();

            const entryPassword =
                document
                    .getElementById("entryPassword")
                    .value
                    .trim();

            const message =
                document.getElementById(
                    "joinMessage"
                );

            if (
                !playerName ||
                !quizId ||
                !entryPassword
            ) {
                message.textContent =
                    "Please fill in all fields.";
                return;
            }

            message.textContent =
                "Joining CIPHERX quiz...";

            try {

                /*
                 * First get the quiz using Quiz ID.
                 */

                const quizResponse =
                    await fetch(
                        API_BASE_URL +
                        "/api/quizzes/" +
                        quizId
                    );

                if (!quizResponse.ok) {
                    message.textContent =
                        "Quiz not found.";
                    return;
                }

                const quiz =
                    await quizResponse.json();

                /*
                 * Check entry password.
                 */

                if (
                    quiz.entryPassword !==
                    entryPassword
                ) {
                    message.textContent =
                        "Invalid quiz password.";
                    return;
                }

                /*
                 * Save player session.
                 */

                sessionStorage.setItem(
                    "cipherxPlayerName",
                    playerName
                );

                sessionStorage.setItem(
                    "cipherxQuizId",
                    quizId
                );

                sessionStorage.setItem(
                    "cipherxEntryPassword",
                    entryPassword
                );

                /*
                 * Join backend.
                 *
                 * Backend currently expects
                 * quizCode, not quizId.
                 */

                const joinResponse =
                    await fetch(
                        API_BASE_URL +
                        "/api/players/join/" +
                        quiz.quizCode,
                        {
                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body: JSON.stringify({
                                playerName:
                                    playerName,

                                entryPassword:
                                    entryPassword
                            })
                        }
                    );

                if (!joinResponse.ok) {

                    const errorText =
                        await joinResponse.text();

                    /*
                     * If player already exists,
                     * continue to lobby.
                     */

                    if (
                        !errorText
                            .toLowerCase()
                            .includes(
                                "already exists"
                            )
                    ) {
                        message.textContent =
                            errorText ||
                            "Unable to join quiz.";

                        return;
                    }
                }

                message.textContent =
                    "Joined successfully!";

                setTimeout(function () {

                    window.location.href =
                        "lobby.html?quizId=" +
                        quizId;

                }, 500);

            } catch (error) {

                console.error(
                    "CIPHERX Join Error:",
                    error
                );

                message.textContent =
                    "Backend connection failed.";
            }
        }
    );
}


/* =========================================================
   LOBBY
   ========================================================= */

const lobbyQuizId =
    document.getElementById(
        "lobbyQuizId"
    );

const lobbyPlayerName =
    document.getElementById(
        "lobbyPlayerName"
    );

const playerCount =
    document.getElementById(
        "playerCount"
    );

const lobbyStatus =
    document.querySelector(
        ".lobby-status"
    );

const waitingText =
    document.querySelector(
        ".waiting-indicator p"
    );


if (
    lobbyQuizId &&
    lobbyPlayerName
) {

    const quizId =
        sessionStorage.getItem(
            "cipherxQuizId"
        );

    const playerName =
        sessionStorage.getItem(
            "cipherxPlayerName"
        );

    lobbyQuizId.textContent =
        quizId || "---";

    lobbyPlayerName.textContent =
        playerName || "---";


    /*
     * Check quiz status immediately.
     */

    checkQuizStatus(quizId);


    /*
     * Check every 2 seconds.
     *
     * This makes the lobby detect when
     * admin starts the quiz.
     */

    setInterval(function () {

        checkQuizStatus(quizId);

    }, 2000);
}


/* =========================================================
   CHECK QUIZ STATUS
   ========================================================= */

async function checkQuizStatus(quizId) {

    if (!quizId) {
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
            return;
        }

        const quiz =
            await response.json();

        console.log(
            "CIPHERX Quiz Status:",
            quiz
        );


        /*
         * Update player count if available.
         */

        if (
            playerCount &&
            Array.isArray(quiz.players)
        ) {

            playerCount.textContent =
                quiz.players.length;
        }


        /*
         * QUIZ STARTED
         */

        if (quiz.active === true) {

            if (lobbyStatus) {

                lobbyStatus.textContent =
                    "Quiz started! Get ready...";
            }

            if (waitingText) {

                waitingText.textContent =
                    "Loading quiz...";
            }

            /*
             * Prevent repeated redirects.
             */

            if (
                !sessionStorage.getItem(
                    "cipherxQuizStarted"
                )
            ) {

                sessionStorage.setItem(
                    "cipherxQuizStarted",
                    "true"
                );

                setTimeout(function () {

                    window.location.href =
                        "quiz.html?quizId=" +
                        quizId;

                }, 700);
            }

            return;
        }


        /*
         * QUIZ NOT STARTED
         */

        if (lobbyStatus) {

            lobbyStatus.textContent =
                "Waiting for the administrator to start the quiz...";
        }

        if (waitingText) {

            waitingText.textContent =
                "Waiting for quiz to start";
        }

    } catch (error) {

        console.error(
            "CIPHERX Lobby Error:",
            error
        );
    }
}