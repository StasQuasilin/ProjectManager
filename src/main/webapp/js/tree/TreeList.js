/**
 * Created by szpt_user045 on 26.02.2019.
 */
var title;
var childBox;
var childInstance;
var addChildButton;
$(document).ready(function(){
    title = document.getElementById('tree-title');
    addChildButton = document.getElementById('add-child');
    childBox = document.getElementById('child-box');
    childInstance = document.getElementById('task-child-instance');
    var search = window.location.search.substring(1);
    if (search.length > 0) {
        var split = search.split('&');
        if (split.length) {
            for (var p in split) {
                var pair = split[p].split('=');
                if (pair[0] == 'project') {
                    showProject(pair[1]);
                }
            }
        }
    } else {
        showTree();
    }
});
function showTask(id){
    var p = [];
    p.task = id;
    showTree(p);
}

function showProject(id){
    var p = [];
    p.project = id;
    showTree(p);
}

function showTree(parameters){
    var xhr = PostAPI('/api/tree/list', parameters);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200){
            var answer = JSON.parse(xhr.responseText);
            if (answer){
                console.log(answer);
                title.innerHTML='';

                var tasks = buildTitle(answer);
                childBox.innerHTML = '';
                for (var i in tasks){
                    addTask(tasks[i]);
                }
            }
        }
    }
}
function addTask(task){
    var span = childInstance.cloneNode(true);
    getChildElemById(span, 'child-title').innerText = task.title;
    span.style.display='block';
    childBox.appendChild(span);
    span.id = task.id;
    span.onclick = function(){
        showTask(this.id);
    }
}
function buildTitle(json){
    if (json.title){
        var span =document.createElement('span');
        span.innerText = json.title;
        span.setAttribute('class', 'tree-title-item');
        span.id = json.id;
        if (json.child) {
            span.onclick = function () {
                showTask(this.id);
            };
        }
        title.appendChild(span);
    }
    if (json.child){
        title.appendChild(document.createTextNode('/'));
        buildTitle(json.child);
    } else{
        addChildButton.onclick=function(){
            addChild(json.id);
        };
        return json['tasks'];
    }
}
function addChild(parent){
    var p = [];
    p.parent = parent;
}
