var evChart;
var reactionChart;

function simulate() {
    return fetch("/simulate").then(function (responce) {
        return responce.json();
    });
}

function formCharts(evTimeCtx, reactionCtx) {
    simulate().then(function (responce) {
        evChart = new Chart(evTimeCtx, {
            type: 'line',
            data: {
                datasets: [{
                    label: 'EvTime / Intensity',
                    data: responce[0],
                    borderColor: "#3e95cd",
                    fill: false
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        id: 'EvTime',
                        type: 'linear',
                        position: 'left'
                    }],
                    xAxes: [{
                        id: 'Intensity',
                        type: 'linear',
                        position: 'bottom'
                    }]
                }
            }
        });
        reactionChart = new Chart(reactionCtx, {
            type: 'line',
            data: {
                datasets: [{
                    label: 'Reaction time / Intensity',
                    data: responce[1],
                    borderColor: "#cd3242",
                    fill: false
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        id: 'EvoTime',
                        type: 'linear',
                        position: 'left'
                    }],
                    xAxes: [{
                        id: 'Intensity',
                        type: 'linear',
                        position: 'bottom'
                    }]
                }
            }
        });
    })
}

document.addEventListener("DOMContentLoaded", function(event) {
    var evTimeCtx  = document.getElementById("evTimeChart").getContext('2d');
    var reactionCtx   = document.getElementById("reactionChart").getContext('2d');
    formCharts(evTimeCtx, reactionCtx);
});
