let searchBox, indexP, digits;

function indexOf( txt, search, start ) {
    if( search == null  ||  typeof search !== 'string' ) {
        return -1;
    }
    if( txt.length < start ) {
        return -1;
    }
    if( search.length == 0 ) {
        return 0;
    }
    for( let i=start; i<txt.length; i++ ) {
        for( let j=0; j<search.length; j++ ) {
            if( search.charAt(j) !== txt.charAt(i+j) ) {
                break;
            }
            if( j == search.length-1 ) {
                return i-1;
            }
        }
    }
    return -1;
}

async function searchItUp() {
    let search = searchBox.value();
    let index;
    if (digits) {
        index = indexOf(digits, search, 2);
    }
    if( index === 0 ) {
        index = 'Searching';
    }
    if( index === -1 ) {
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