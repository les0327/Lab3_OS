var evChart;
var freeTimeChart;
var distChart;

function getEvTimePoints() {
    return fetch("/getEvTimePoints").then(function (responce) {
        return responce.json();
    });
}

function getFreeTimePoints() {
    return fetch("/getFreeTimePoints").then(function (responce) {
        return responce.json();
    });
}

function dist() {
    return fetch("/dist").then(function (responce) {
        return responce.json();
    })
}

function formCharts(evTimeCtx, freeTimeCtx, distCtx) {
    getEvTimePoints().then(function (responce) {
        evChart = new Chart(evTimeCtx, {
            type: 'line',
            data: {
                datasets: [{
                    label: 'EvTime / Intensity',
                    data: responce,
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
        getFreeTimePoints().then(function (responce) {
            freeTimeChart = new Chart(freeTimeCtx, {
                type: 'line',
                data: {
                    datasets: [{
                        label: 'Free Time % / Intensity',
                        data: responce,
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
            })
        })
        dist().then(function (responce) {
            distChart = new Chart(distCtx, {
                type: 'line',
                data: {
                    datasets: [{
                        label: 'N / Tw',
                        data: responce,
                        borderColor: "#5dcd29",
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
    })
}

document.addEventListener("DOMContentLoaded", function (event) {
    var evTimeCtx = document.getElementById("evTimeChart").getContext('2d');
    var freeTimeCtx = document.getElementById("freeTimeChart").getContext('2d');
    var distCtx = document.getElementById("distChart").getContext('2d');
    formCharts(evTimeCtx, freeTimeCtx, distCtx);
});
