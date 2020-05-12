let coverlet;
let modal;
let title;
let content;
let filterHolder;

$(document).ready(function(){
    coverlet = document.getElementById('coverlet');
    modal = document.getElementById('modal');
    title = document.getElementById('title');
    content = document.getElementById('content');
    filterHolder = document.getElementById('filterHolder');
    coverlet.style.display='none';
    modal.style.display = 'none';
});

function loadPage(url, parameters){
    if (url) {
        coverlet.style.display='block';
        if (parameters === undefined){
            parameters = null;
        }
        PostReq(url, parameters, function (a) {
            if (typeof unsubscribe === 'function'){
                unsubscribe();
            }
            subscriber.unsubscribeAll();
            $(content).empty();
            $(content).html(a);
            $(title).html(GetChildElemById(content, 'title'));
            $(title).append(GetChildElemById(content, 'title-content'));
            $(filterHolder).empty();
            let filter = GetChildElemById(content, 'filterShell');
            if (filterHolder){
                $(filterHolder).html(filter);
            }
            coverlet.style.display='none';
        }, function(e){
            coverlet.style.display='none';
        })
    }
}
function loadModal(url, parameters){
    if (url) {
        coverlet.style.display='block';
        if (parameters === undefined){
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