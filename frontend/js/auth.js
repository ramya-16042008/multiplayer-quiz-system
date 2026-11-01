const API_BASE_URL = "http://localhost:8080";


/* =========================================================
   SAVE LOGIN SESSION
========================================================= */

function saveAuthSession(
    username,
    role,
    token
) {

    sessionStorage.setItem(
        "cipherxUsername",
        username
    );

    sessionStorage.setItem(
        "cipherxRole",
        role
    );

    sessionStorage.setItem(
        "cipherxToken",
        token
    );

    sessionStorage.setItem(
        "cipherxAuthenticated",
        "true"
    );
}


/* =========================================================
   CHECK AUTHENTICATION
========================================================= */

function isAuthenticated() {

    return (
        sessionStorage.getItem(
            "cipherxAuthenticated"
        ) === "true"
        &&
        !!sessionStorage.getItem(
            "cipherxToken"
        )
    );
}


/* =========================================================
   GET CURRENT USER
========================================================= */

function getCurrentUsername() {

    return sessionStorage.getItem(
        "cipherxUsername"
    );
}


function getCurrentRole() {

    return sessionStorage.getItem(
        "cipherxRole"
    );
}


function getAuthToken() {

    return sessionStorage.getItem(
        "cipherxToken"
    );
}


/* =========================================================
   LOGOUT
========================================================= */

function logout() {

    const token =
        getAuthToken();

    if (token) {

        fetch(
            API_BASE_URL +
            "/api/auth/logout",
            {
                method: "POST",

                headers: {
                    "Authorization":
                        "Bearer " + token
                }
            }
        ).catch(
            function () {}
        );
    }


    sessionStorage.removeItem(
        "cipherxUsername"
    );

    sessionStorage.removeItem(
        "cipherxRole"
    );

    sessionStorage.removeItem(
        "cipherxToken"
    );

    sessionStorage.removeItem(
        "cipherxAuthenticated"
    );


    window.location.href =
        "login.html";
}


/* =========================================================
   ADMIN LOGIN
========================================================= */

const adminLoginForm =
    document.getElementById(
        "adminLoginForm"
    );


if (adminLoginForm) {

    adminLoginForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            const username =
                document
                    .getElementById(
                        "username"
                    )
                    .value
                    .trim();


            const password =
                document
                    .getElementById(
                        "password"
                    )
                    .value;


            const message =
                document.getElementById(
                    "loginMessage"
                );


            /* -------------------------
               VALIDATION
            ------------------------- */

            if (
                !username ||
                !password
            ) {

                message.style.color =
                    "#fca5a5";

                message.textContent =
                    "Please enter username and password.";

                return;
            }


            message.style.color =
                "#a4adbd";

            message.textContent =
                "Connecting to CIPHERX server...";


            /* -------------------------
               LOGIN REQUEST
            ------------------------- */

            try {

                const response =
                    await fetch(
                        API_BASE_URL +
                        "/api/auth/login",
                        {
                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body:
                                JSON.stringify({
                                    username:
                                        username,

                                    password:
                                        password
                                })
                        }
                    );


                const data =
                    await response.json();


                console.log(
                    "CIPHERX Login Response:",
                    data
                );


                /* -------------------------
                   SUCCESS
                ------------------------- */

                if (
                    response.ok &&
                    data.success &&
                    data.role &&
                    data.token
                ) {

                    /*
                     * IMPORTANT:
                     * Save the backend token.
                     */

                    saveAuthSession(
                        username,
                        data.role,
                        data.token
                    );


                    console.log(
                        "CIPHERX Token Saved:",
                        sessionStorage.getItem(
                            "cipherxToken"
                        )
                    );


                    message.style.color =
                        "#72e6a5";

                    message.textContent =
                        "Login successful. Opening dashboard...";


                    setTimeout(
                        function () {

                            window.location.href =
                                "dashboard.html";

                        },
                        500
                    );


                    return;
                }


                /* -------------------------
                   LOGIN FAILED
                ------------------------- */

                message.style.color =
                    "#fca5a5";

                message.textContent =
                    data.message ||
                    "Invalid username or password.";

            }


            /* -------------------------
               CONNECTION ERROR
            ------------------------- */

            catch (error) {

                console.error(
                    "CIPHERX API Error:",
                    error
                );


                message.style.color =
                    "#fca5a5";

                message.textContent =
                    "Backend connection failed. Start the CIPHERX server.";
            }

        }
    );
}