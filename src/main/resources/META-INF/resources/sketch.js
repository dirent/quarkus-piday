let searchBox, indexP, digits;

async function searchItUp() {
    let search = searchBox.value();
    let index;
    if (digits) {
        index = digits.indexOf(search, 2) - 1;
    }
    if( index === 0 ) {
        index = 'Searching';
    }
    if( index === -2 ) {
        index = 'Not found';
    } 
    indexP.html(index);
}

async function setup() {
    noCanvas();

    let response = await fetch("pi");
    if (response.ok) {
        digits = await response.text();
    } else {
        console.error("Could not load digits. HTTP-Status: " + response.status);
    }
    searchBox = createInput('');
    indexP = createP('Searching').class("result");
    searchBox.input(searchItUp);
}