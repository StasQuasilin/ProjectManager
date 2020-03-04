var coverlet;
var modal;
var title;
var content;

$(document).ready(function(){
    coverlet = document.getElementById('coverlet');
    modal = document.getElementById('modal');
    title = document.getElementById('title');
    content = document.getElementById('content');
    coverlet.style.display='none';
    modal.style.display = 'none';
});

function loadPage(url, parameters){
    if (url) {
        if (typeof unsubscribe === 'function'){
            unsubscribe();
        }
        coverlet.style.display='block';
        if (parameters == undefined){
            parameters = null;
        }
        PostReq(url, parameters, function (a) {
            $(content).empty();
            $(content).html(a);
            $(title).html(GetChildElemById(content, 'title'));
            $(title).append(GetChildElemById(content, 'title-content'));
            coverlet.style.display='none';
        }, function(e){
            coverlet.style.display='none';
        })
    }
}
function loadModal(url, parameters){
    if (url) {
        coverlet.style.display='block';
        if (parameters == undefined){
            parameters = null;
        }
        PostReq(url, parameters, function (a) {
            $(modal).empty();
            $(modal).html(a);
            coverlet.style.display='none';
            modal.style.display='block';
        }, function(e){
            coverlet.style.display='none';
        })
    }
}
function closeModal(){
    modal.innerHTML = '';
    modal.style.display='none';
}