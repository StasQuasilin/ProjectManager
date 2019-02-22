/**
 * Created by szpt_user045 on 22.02.2019.
 */
var ctx;
function valid(input){
    if(input.value == ''){
        input.setAttribute('wrong', 'wrong');
        input.onfocus = function(){
            this.removeAttribute('wrong');
        };
        return false;
    }
    return true;
}
function toBase64(str){
    return btoa(encodeURIComponent(str).replace(/%([0-9A-F]{2})/g,
        function toSolidBytes(match, p1) {
            return String.fromCharCode('0x' + p1);
        }));
}
function PostAPI(address, params){
    var body = [];

    if (params != null){
        for (var k in params){
            body[body.length] = k +'='+params[k];
        }

    }

    var xhr = new XMLHttpRequest();
    xhr.open('POST', ctx + address, true);
    xhr.send(body.join('&'));
    return xhr;
}
