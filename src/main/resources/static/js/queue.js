var pEvTime;
var pReactionTime;
var pRatio;
var pTime;
var ulQueue;
var liCurrTask;
var ulSolved;

function step() {
    return fetch("/step").then(function (responce) {
        return responce.json();
    });
}

function arrToLi(arr) {
    return arr.map(function (value) {
        var li = document.createElement("li");
        li.appendChild(document.createTextNode(taskToString(value)));
        li.classList.add("list-group-item");
        return li;
    });
}

function clear() {
    pEvTime.innerHTML = '';
    pReactionTime.innerHTML = '';
    pRatio.innerHTML = '';
    pTime.innerHTML = '';
    ulQueue.innerHTML = '';
    liCurrTask.innerHTML = '';
    ulSolved.innerHTML = '';
}

function taskToString(task) {
    return "Task{InTime=" + Number(task.inTime).toFixed(3)
        + " SolveTime=" + Number(task.solveTime).toFixed(3)
        + " OnSolving=" + Number(task.onSolving).toFixed(3)
        + " OutTime=" + Number(task.outTime).toFixed(3) + "}";

}

function model() {
    step().then(function (model) {
        clear();
        pEvTime.appendChild(document.createTextNode(model.evTime));
        pReactionTime.appendChild(document.createTextNode(model.reactionTime));
        pRatio.appendChild(document.createTextNode(model.ratio));
        pTime.appendChild(document.createTextNode(model.t));
        liCurrTask.appendChild(document.createTextNode(taskToString(model.currTask)));
        var queueLi = arrToLi(model.queue.tasks);
        var solvedLi = arrToLi(model.solvedTasks);
        queueLi.forEach(function (value) {
            ulQueue.appendChild(value);
        });
        solvedLi.forEach(function (value) {
            ulSolved.insertBefore(value, parent.firstChild);
        });
    });
}

document.addEventListener("DOMContentLoaded", function (event) {
    pEvTime = document.getElementById("evTime");
    pReactionTime = document.getElementById("reactionTime");
    pRatio = document.getElementById("ratio");
    pTime = document.getElementById("time");
    ulQueue = document.getElementById("queue");
    liCurrTask = document.getElementById("currTask");
    ulSolved = document.getElementById("solved");

    setInterval(model, 1000);
});


