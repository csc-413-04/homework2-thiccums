// These are console log types
console.log('hello world');
// console.warn('this is a warning');
// console.error('this is an error');

let myVariable;
const myOtherVariable = 4;
myVariable = [8, 23, 'asd'];

const myObject = {};
myObject.a = 8;
myObject.b = 9;

console.log(myVariable);
console.log(myObject);

// functions 
function myFunction() {
    const oReq = new XMLHttpRequest();
    oReq.addEventListener("load", (response) => {
        // this will not execute until the response is done
        const data = JSON.parse(oReq.responseText);
        const humidity = data.query.results.channel.atmosphere.humidity;
        console.log(humidity);
        document.getElementById('humidity').innerHTML = humidity
        // selects elements by id from the document tree
        //document.getElementById('output').innerHTML = oReq.responseText;
    });
    oReq.open("GET", "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22nome%2C%20ak%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
    oReq.send();
}

function pushCoinNum() {
    const oReq = new XMLHttpRequest();
    oReq.addEventListener("load", (response) => {
        // this will not execute until the response is done
        const data = JSON.parse(oReq.responseText);
        console.log(data);
        // selects elements by id from the document tree
        document.getElementById('output').innerHTML = oReq.responseText;
    });
    oReq.open("GET", "/counterUpdate");
    oReq.send();
}

function statusUpdate() {
    const oReq = new XMLHttpRequest();
    oReq.addEventListener("load", (response) => {
        // this will not execute until the response is done
        const data = JSON.parse(oReq.responseText);
        console.log(data);
        // selects elements by id from the document tree
        document.getElementById('output').innerHTML = oReq.responseText;
    });
    oReq.open("GET", "/status");
    oReq.send();
}

myFunction();

// https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest/Using_XMLHttpRequest

// arrow functions
const myArrowFunction = () => {
    setTimeout(() => {
        statusUpdate();
        myArrowFunction();
    }, 200);
};

myArrowFunction();
