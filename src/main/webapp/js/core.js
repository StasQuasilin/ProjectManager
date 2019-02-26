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
function PostAPI(address, params, handler){
    var body = [];

    if (params != null){
        for (var k in params){
            body[body.length] = k +'='+params[k];
        }

    }

    var xhr = new XMLHttpRequest();
    xhr.open('POST', ctx + address, true);
    xhr.send(body.join('&'));
    if (!handler){
        return xhr;
    } else {
        xhr.onreadystatechange = function(){
            if (xhr.readyState == 4 && xhr.status == 200){
                try {
                    handler(JSON.parse(xhr.responseText));
                } catch(e){
                    console.error("Error parse " + xhr.responseText);
                }


            }
        }
    }
}
function validEmail(inp){
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (re.test(inp.value)){
        return true;
    } else {
        inp.setAttribute('wrong', 'wrong');
        inp.onfocus = function(){
            this.removeAttribute('wrong');
        };
        return false;
    }
}
function getChildElemById(parent, childId){
    var elems = parent.getElementsByTagName("*");
    for (var i = 0; i < elems.length; i++){
        if (elems[i].id == childId){
            return elems[i];
        }
    }
}
