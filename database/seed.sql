-- CIPHERX Sample Data
-- This file is used only for development/testing.

-- =========================
-- USERS
-- =========================

INSERT INTO users (username, email)
VALUES
    ('cipherx_admin', 'admin@cipherx.com'),
    ('player_one', 'player1@cipherx.com'),
    ('player_two', 'player2@cipherx.com');


-- =========================
-- ADMIN
-- =========================

INSERT INTO admins (user_id)
SELECT user_id
FROM users
WHERE username = 'cipherx_admin';


-- =========================
-- PLAYERS
-- =========================

INSERT INTO players (user_id)
SELECT user_id
FROM users
WHERE username IN ('player_one', 'player_two');


-- =========================
-- QUIZ
-- =========================

INSERT INTO quizzes
(title, description, quiz_code, entry_password, time_per_question, active)
VALUES
(
    'Cyber Security Basics',
    'A beginner-level cybersecurity quiz for CIPHERX testing.',
    'CYB123',
    'Cipher@123',
    20,
    FALSE
);


-- =========================
-- QUESTIONS
-- =========================

INSERT INTO questions
(question_text, correct_option, points)
VALUES
(
    'What does CIA stand for in cybersecurity?',
    0,
    10
),
(
    'Which protocol is commonly used for secure web communication?',
    1,
    10
),
(
    'What does SQL Injection target?',
    2,
    10
);


-- =========================
-- QUESTION OPTIONS
-- =========================

INSERT INTO question_options (question_id, option_text)
VALUES
    (1, 'Confidentiality, Integrity, Availability'),
    (1, 'Control, Internet, Access'),
    (1, 'Cyber, Intelligence, Authentication'),
    (1, 'Centralized Information Architecture'),

    (2, 'HTTP'),
    (2, 'HTTPS'),
    (2, 'FTP'),
    (2, 'SMTP'),

    (3, 'Operating System'),
    (3, 'Network Cable'),
    (3, 'Database Query'),
    (3, 'Monitor');


-- =========================
-- SAMPLE SCORES
-- =========================

INSERT INTO scores
(player_id, quiz_id, total_score, correct_answers, wrong_answers, total_response_time)
VALUES
(
    (SELECT user_id FROM users WHERE username = 'player_one'),
    (SELECT quiz_id FROM quizzes WHERE quiz_code = 'CYB123'),
    30,
    3,
    0,
    42000
),
(
    (SELECT user_id FROM users WHERE username = 'player_two'),
    (SELECT quiz_id FROM quizzes WHERE quiz_code = 'CYB123'),
    20,
    2,
    1,
    51000
);