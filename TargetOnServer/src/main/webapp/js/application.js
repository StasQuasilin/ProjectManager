let titleHolder;
let contentHolder;
let modal;
let coverlet;
let currentPage;
let modals = [];
let logoutUrl;
let welcomeUrl;

$(document).ready(function () {
    titleHolder = document.getElementById('titleHolder');
    contentHolder = document.getElementById('contentHolder');
    modal = document.getElementById('modalLayer');
    coverlet = document.getElementById('coverlet');
    hideModalLayer();
    openLastPage();
});

function openLastPage() {
    let last = localStorage.getItem('last-page-for-' + user);
    if (last){
        loadPage(last)
    } else {
        loadPage(welcomeUrl);
    }
}

function loadPage(url, params) {
    if (url && currentPage !== url) {
        if (typeof subscriber.unsubscribe !== "undefined"){
            subscriber.unsubscribe();
        }
        coverlet.style.display = 'block';
        localStorage.setItem('last-page-for-' + user, url);
        currentPage = url;
        PostReq(url, params, function (content) {
            $(contentHolder).empty();
            $(contentHolder).html(content);
            $(titleHolder).html(GetChildElemById(contentHolder, 'title'));
            $(titleHolder).append(GetChildElemById(contentHolder, 'titleContent'));
            coverlet.style.display = 'none';
        }, function (message) {
            $(contentHolder).empty();
            $(titleHolder).html(message);
            $(contentHolder).html('<div style="display: flex; width: 100%; height: 100%">' +
                '<div style="flex 1 0 auto; width: 100%; height: 100%; text-align: center; vertical-align: middle">' +
                '<h2>' + message + '</h2></div></div>');
            coverlet.style.display = 'none';
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
function logout() {
    PostReq(logoutUrl, null, function (a) {
        subscriber.shutdown();
        location.href = a + '?lang=' + window.navigator.language.slice(0, 2);
    });
}
function removeAccount() {
    PostApi(removeAccountApi, null, function (a) {
        subscriber.shutdown();
        if (a.status === 'success'){
            location.href = a.redirect + '?lang=' + window.navigator.language.slice(0, 2);
        }
    })
}
