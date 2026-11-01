/*
 * CIPHERX Reports
 *
 * Temporary frontend report data.
 * Later this will be loaded from
 * the Java backend + PostgreSQL.
 */


const demoReportPlayers = [
    {
        name: "Player 1",
        score: 90,
        status: "Completed"
    },
    {
        name: "Player 2",
        score: 80,
        status: "Completed"
    },
    {
        name: "Player 3",
        score: 70,
        status: "Completed"
    }
];


function loadReports() {

    const reportsList =
        document.getElementById(
            "reportsList"
        );

    if (!reportsList) {
        return;
    }


    const currentQuiz =
        JSON.parse(
            localStorage.getItem(
                "cipherxCurrentQuiz"
            ) || "null"
        );


    if (!currentQuiz) {

        reportsList.innerHTML = `
            <div class="empty-state">

                <h3>
                    No reports available
                </h3>

                <p>
                    Completed quiz reports will
                    appear here.
                </p>

            </div>
        `;

        return;
    }


    reportsList.innerHTML = `

        <div class="quiz-row">

            <strong>
                ${currentQuiz.title}
            </strong>

            <span>
                ${currentQuiz.quizCode}
            </span>

            <span>
                ${demoReportPlayers.length}
            </span>

            <a
                href="quiz-report.html"
                class="btn secondary">
                View Report
            </a>

        </div>

    `;
}


function loadQuizReport() {

    const currentQuiz =
        JSON.parse(
            localStorage.getItem(
                "cipherxCurrentQuiz"
            ) || "null"
        );


    const title =
        document.getElementById(
            "reportQuizTitle"
        );

    const players =
        document.getElementById(
            "reportPlayers"
        );

    const questions =
        document.getElementById(
            "reportQuestions"
        );

    const averageScore =
        document.getElementById(
            "averageScore"
        );

    const completionRate =
        document.getElementById(
            "completionRate"
        );

    const playerList =
        document.getElementById(
            "reportPlayersList"
        );


    if (!playerList) {
        return;
    }


    if (currentQuiz && title) {

        title.textContent =
            `${currentQuiz.title} • ${currentQuiz.quizCode}`;
    }


    const questionData =
        JSON.parse(
            localStorage.getItem(
                "cipherxQuestions"
            ) || "[]"
        );


    const totalPlayers =
        demoReportPlayers.length;


    const totalQuestions =
        questionData.length;


    const totalScore =
        demoReportPlayers.reduce(
            (sum, player) =>
                sum + player.score,
            0
        );


    const average =
        totalPlayers > 0
            ? Math.round(
                totalScore / totalPlayers
            )
            : 0;


    if (players) {
        players.textContent =
            totalPlayers;
    }


    if (questions) {
        questions.textContent =
            totalQuestions;
    }


    if (averageScore) {
        averageScore.textContent =
            average;
    }


    if (completionRate) {
        completionRate.textContent =
            totalPlayers > 0
                ? "100%"
                : "0%";
    }


    playerList.innerHTML = "";


    demoReportPlayers
        .sort(
            (a, b) =>
                b.score - a.score
        )
        .forEach(
            (player, index) => {

                const row =
                    document.createElement(
                        "div"
                    );

                row.className =
                    "quiz-row";


                row.innerHTML = `

                    <span>
                        ${index + 1}
                    </span>

                    <strong>
                        ${player.name}
                    </strong>

                    <span>
                        ${player.score}
                    </span>

                    <span class="status">
                        ${player.status}
                    </span>

                `;


                playerList.appendChild(row);
            }
        );
}


loadReports();
loadQuizReport();