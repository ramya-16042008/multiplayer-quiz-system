const leaderboard =
    document.getElementById("leaderboard");


if (leaderboard) {

    /*
     * Temporary demo leaderboard.
     * Later this will receive live data
     * through the Java WebSocket backend.
     */

    const players = [
        {
            name: "Player 1",
            score: 100
        },
        {
            name: "Player 2",
            score: 90
        },
        {
            name: "Player 3",
            score: 80
        }
    ];


    players.sort(
        (a, b) => b.score - a.score
    );


    leaderboard.innerHTML = `
        <div class="leaderboard-row header">
            <span>Rank</span>
            <span>Player</span>
            <span>Score</span>
        </div>
    `;


    players.forEach((player, index) => {

        const row =
            document.createElement("div");

        row.className = "leaderboard-row";

        row.innerHTML = `
            <span>${index + 1}</span>
            <strong>${player.name}</strong>
            <span>${player.score}</span>
        `;

        leaderboard.appendChild(row);
    });
}