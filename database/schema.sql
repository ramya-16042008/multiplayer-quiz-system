CREATE DATABASE cipherx;

-- Connect to cipherx database before running the tables below.

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255)
);

CREATE TABLE admins (
    user_id INTEGER PRIMARY KEY
        REFERENCES users(user_id)
        ON DELETE CASCADE
);

CREATE TABLE players (
    user_id INTEGER PRIMARY KEY
        REFERENCES users(user_id)
        ON DELETE CASCADE
);

CREATE TABLE quizzes (
    quiz_id SERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    quiz_code VARCHAR(20) NOT NULL UNIQUE,
    entry_password VARCHAR(255) NOT NULL,
    time_per_question INTEGER NOT NULL,
    active BOOLEAN DEFAULT FALSE
);

CREATE TABLE questions (
    question_id SERIAL PRIMARY KEY,
    quiz_id INTEGER REFERENCES quizzes(quiz_id) ON DELETE CASCADE,
    question_text TEXT NOT NULL,
    correct_option INTEGER NOT NULL,
    points INTEGER NOT NULL
);

CREATE TABLE question_options (
    question_id INTEGER NOT NULL
        REFERENCES questions(question_id)
        ON DELETE CASCADE,
    option_text TEXT NOT NULL
);

CREATE TABLE answers (
    answer_id SERIAL PRIMARY KEY,
    quiz_id INTEGER,
    question_id INTEGER NOT NULL,
    player_id INTEGER NOT NULL,
    selected_option INTEGER NOT NULL,
    correct BOOLEAN DEFAULT FALSE,
    response_time BIGINT
);

CREATE TABLE quiz_sessions (
    session_id SERIAL PRIMARY KEY,
    quiz_id INTEGER NOT NULL
        REFERENCES quizzes(quiz_id)
        ON DELETE CASCADE,
    current_question_index INTEGER DEFAULT 0,
    active BOOLEAN DEFAULT FALSE
);

CREATE TABLE scores (
    score_id SERIAL PRIMARY KEY,
    player_id INTEGER NOT NULL,
    player_name VARCHAR(100),
    quiz_id INTEGER NOT NULL,
    total_score INTEGER DEFAULT 0,
    correct_answers INTEGER DEFAULT 0,
    wrong_answers INTEGER DEFAULT 0,
    total_response_time BIGINT DEFAULT 0
);

CREATE TABLE reports (
    report_id SERIAL PRIMARY KEY,
    quiz_id INTEGER NOT NULL,
    quiz_title VARCHAR(150),
    total_players INTEGER DEFAULT 0,
    total_questions INTEGER DEFAULT 0
);