// creating original checkerboard
let boardArr = [...Array(25)].map(() => [...Array(25)]);
let table =  document.createElement("table");
let state = true;
for (i=0; i < 25; i++) {
    let row = document.createElement("tr");
    for (j=0; j < 25; j++) {
        let col = document.createElement("td");
        col.id = "cell"+i+" "+j;
        boardArr[i][j] = state;

        if (state) {
            col.classList.add("alive");
            state = false;
        }
        else { state = true;}

        row.appendChild(col);
    }
    table.appendChild(row);
}
let container = document.getElementById("tableContainer");
container.appendChild(table);

//creating a copy of original board
let currentBoard = [...Array(25)].map(() => [...Array(25)]);
function createCopyArr() {
    let state2 = true;
    for (x=0; x < 25; x++) {
        for (y=0; y < 25; y++) {
            currentBoard[x][y] = state2;
            if (state2) {
                state2 = false;
            }
            else { state2 = true;}
        }
    }
}
createCopyArr();

//function to update class list of each element
function updateBoard(arr) {
    for (k=0; k < 25; k++) {
        for (l=0; l < 25; l++) {
            let elem = document.getElementById("cell"+k+" "+l);
            if ( (arr[k][l]) && (currentBoard[k][l]) ) {
                elem.classList.add("alive");
            }
            else if ( (arr[k][l]) && (!currentBoard[k][l]) ) {
                elem.classList.add("alive");
            }
            else if ( (!arr[k][l]) && (currentBoard[k][l]) ) {
                elem.classList.remove("alive");
            }
            else {
                elem.classList.remove("alive");
            }
        }
    }
}

function resetBoard() {
    if (goClicked === 1) {
        clearInterval(goTimer);
        goClicked = 0;
    }
    updateBoard(boardArr);
    createCopyArr();
}

function randomBoard() {
    if (goClicked === 1) {
        clearInterval(goTimer);
        goClicked = 0;
    }

    for (m=0; m < 25; m++) {
        for (n=0; n < 25; n++) {
            let randomIndex = Math.round(Math.random() * 100);  //getting a random number between 0 and 100
            if (randomIndex > 50) {
                currentBoard[m][n] = true;
            }
            else if (randomIndex <= 50) {
                currentBoard[m][n] = false;
            }
        }
    }
    updateBoard(currentBoard);
}

function stepClick() {
    let newBoard = stepBoard(currentBoard);
    updateBoard(newBoard);
    currentBoard = newBoard;
}

let goTimer;
// counters to keep track of the button's state
let goClicked = 0;
let pauseClicked = 1;

function goInterval() {
    if (goClicked === 0) {
        goClicked = 1;
        pauseClicked = 1;
        goTimer = setInterval(stepClick, 100);
    }
}

function pauseInterval() {
    if (pauseClicked === 1 && goClicked === 1) {
        clearInterval(goTimer);
        goClicked = 0;
        pauseClicked = 0;
    }
}

document.getElementById("stepButton").addEventListener("click", stepClick);
document.getElementById("resetButton").addEventListener("click", resetBoard);
document.getElementById("goButton").addEventListener("click", goInterval);
document.getElementById("pauseButton").addEventListener("click", pauseInterval);
document.getElementById("randomButton").addEventListener("click", randomBoard);