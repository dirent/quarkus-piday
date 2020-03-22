let searchBox;

function searchItUp() {
    console.log( "Search it up:" + searchBox.value() );
}

function setup() {
    noCanvas();

    searchBox = createInput('');
    searchBox.input(searchItUp);
}
