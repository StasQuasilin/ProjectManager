let titleHolder;
let contentHolder;
let modal;
let coverlet;
let currentPage;

$(document).ready(function () {
    titleHolder = document.getElementById('titleHolder');
    contentHolder = document.getElementById('contentHolder');
    modal = document.getElementById('modalLayer');
    hideModalLayer();
})

function loadPage(url, params) {
    if (url && currentPage !== url) {
        currentPage = url;
        PostReq(url, params, function (content) {
            $(contentHolder).empty();
            $(contentHolder).html(content);
        })
    }
}

function hideModalLayer() {
    modal.style.display = 'none';
}