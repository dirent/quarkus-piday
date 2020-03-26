let searchBox, indexP, digits;

async function searchItUp() {
    let search = searchBox.value();
    if( search ) {
        let index;
        let response = await fetch("pi?search="+search);
        if (response.ok) {
            index = await response.text();
        } else {
            console.error("Could not search for digits in pi. HTTP-Status: " + response.status);
        }
        if( index === 0 ) {
            index = 'Searching';
        }
        if( index === -1 ) {
            index = 'Not found';
        }
        indexP.html(index);
    } 
}

async function setup() {
    noCanvas();

    searchBox = createInput('');
    indexP = createP('Searching').class("result");
    searchBox.input(searchItUp);
}