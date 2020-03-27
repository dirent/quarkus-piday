let searchBox, indexP, digits;

function searchItUp() {
    let digits = searchBox.value();
    if( digits ) {
        loadJSON( "pi/search/"+digits, gotResult );
    } 
}

function gotResult(data) {
    let index = data.index;
    if( index === 0 ) {
        index = 'Searching';
    }
    if( index === -1 ) {
        index = 'Not found';
    }
    indexP.html(index);
}

function setup() {
    noCanvas();

    searchBox = createInput('');
    indexP = createP('Searching').class("result");
    searchBox.input(searchItUp);
}