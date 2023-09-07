function stepBoard(arr) {
    
    if ( arr.length === 0 || arr[0].length === 0) {
        return arr;
    }

    let numRows = arr.length;
	let numCols = arr[0].length;
    let newArr = [...Array(numRows)].map(() => [...Array(numCols)]);
    
    for (i=0; i < numRows; i++) {
        for (j=0; j < numCols; j++) {
            if ( !(arr[i][j]) && liveNeighbourCount( [i,j] ) === 3) {
                newArr[i][j] = true;
            }
            else if (arr[i][j] && (liveNeighbourCount( [i,j] ) === 2 || liveNeighbourCount( [i,j] ) === 3)) {
                newArr[i][j] = true;
            }
            else {
                newArr[i][j] = false;
            }
        }
    }
    
    // inner function counts how many neighbours are live
    function liveNeighbourCount(cell) {
        let liveCount = 0;
        let row = cell[0];
        let col = cell[1];

        if (col-1 >= 0) {
            if (arr[row][col-1]) {
                liveCount++;
            }
        }
        if (col+1 < numCols) {
            if (arr[row][col+1]) {
                liveCount++;
            }
        }

        for (k=-1; k < 2; k++) {  //loop starts from -1 to check neighbours to the left
            if (row-1 >= 0 && (col+k >= 0 && col+k < numCols)) {
                if (arr[row-1][k+col]) {
                    liveCount++;
                }
            }
            else {
                continue;
            }
        }

        for (l=-1; l < 2; l++) {
            if (row+1 < numRows && (col+l >= 0 && col+l < numCols)) {
                if (arr[row+1][l+col]) {
                    liveCount++;
                }
            }
            else {
                continue;
            }
        }
        return liveCount; 
    }
    return newArr;
}