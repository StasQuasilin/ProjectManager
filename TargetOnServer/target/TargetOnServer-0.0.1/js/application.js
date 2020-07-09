let titleHolder;
let contentHolder;
let modal;
let coverlet;
let currentPage;
let modals = [];

$(document).ready(function () {
    titleHolder = document.getElementById('titleHolder');
    contentHolder = document.getElementById('contentHolder');
    modal = document.getElementById('modalLayer');
    hideModalLayer();
});

function loadPage(url, params) {
    if (url && currentPage !== url) {
        currentPage = url;
        PostReq(url, params, function (content) {
            $(contentHolder).empty();
            $(contentHolder).html(content);
            $(titleHolder).html(GetChildElemById(contentHolder, 'title'));
            $(titleHolder).append(GetChildElemById(contentHolder, 'titleContent'));
        })
    }
}
function loadModal(url, parameters){
    PostReq(url, parameters, function (answer) {
        $(modal).html(answer);
        modal.style.display = 'block';
    })
}
function hideModalLayer() {
    modal.style.display = 'none';
}
function closeModal(){
    hideModalLayer();
}