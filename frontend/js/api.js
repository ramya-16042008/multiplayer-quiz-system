/*
 * CIPHERX API Client
 *
 * Central place for frontend → Java backend requests.
 */

const API_BASE_URL = "http://localhost:8080";


async function apiRequest(
    endpoint,
    options = {}
) {

    const defaultHeaders = {
        "Content-Type": "application/json"
    };


    const config = {
        ...options,

        headers: {
            ...defaultHeaders,
            ...(options.headers || {})
        }
    };


    try {

        const response =
            await fetch(
                API_BASE_URL + endpoint,
                config
            );


        const contentType =
            response.headers.get(
                "content-type"
            );


        let data;


        if (
            contentType &&
            contentType.includes(
                "application/json"
            )
        ) {

            data =
                await response.json();

        } else {

            data =
                await response.text();
        }


        if (!response.ok) {

            let errorMessage =
                "Request failed";


            if (
                typeof data ===
                "string"
            ) {

                errorMessage =
                    data;

            } else if (
                data &&
                data.message
            ) {

                errorMessage =
                    data.message;
            }


            throw new Error(
                errorMessage
            );
        }


        return data;


    } catch (error) {

        console.error(
            "CIPHERX API Error:",
            error
        );

        throw error;
    }
}


/* =========================
   AUTH API
========================= */

async function loginAdmin(
    username,
    password
) {

    return apiRequest(
        "/api/auth/login",
        {
            method: "POST",

            body: JSON.stringify({
                username,
                password
            })
        }
    );
}


/* =========================
   QUIZ API
========================= */

async function createQuiz(
    quizData
) {

    return apiRequest(
        "/api/quizzes",
        {
            method: "POST",

            body: JSON.stringify(
                quizData
            )
        }
    );
}


async function getQuiz(
    quizId
) {

    return apiRequest(
        `/api/quizzes/${quizId}`
    );
}


async function joinQuiz(
    quizId,
    playerData
) {

    return apiRequest(
        `/api/quizzes/${quizId}/join`,
        {
            method: "POST",

            body: JSON.stringify(
                playerData
            )
        }
    );
}


/* =========================
   QUESTION API
========================= */

async function addQuestion(
    quizId,
    questionData
) {

    return apiRequest(
        `/api/quizzes/${quizId}/questions`,
        {
            method: "POST",

            body: JSON.stringify(
                questionData
            )
        }
    );
}


/* =========================
   REPORT API
========================= */

async function getQuizReport(
    quizId
) {

    return apiRequest(
        `/api/reports/quiz/${quizId}`
    );
}