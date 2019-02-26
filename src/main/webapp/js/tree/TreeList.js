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
    childBox.innerHTML = '';
    PostAPI('/api/tree/list', parameters, function(txt){
        console.log(txt);
        title.innerHTML='';
        buildTitle(txt);

    });
}
function addTask(task){
    var span = childInstance.cloneNode(true);
    var title = getChildElemById(span, 'child-title');
    title.innerText = task.title;
    var menu = getChildElemById(span, 'menu');

    var edit = getChildElemById(menu, 'edit');
    edit.parent = span;
    edit.label=title;
    edit.id=task.id;
    edit.onclick = function(){
        this.label.style.display='none';
        var inp = document.createElement('input');
        inp.value = this.label.innerText;
        inp.parent = this;

        this.parent.appendChild(inp);
        inp.onkeyup=function(){
            if (event.key == 'Escape'){
                this.parent.parent.removeChild(inp);
                this.parent.label.style.display='inline-block';
            } else if (event.key == 'Enter'){
                if (valid(this)) {
                    this.parent.parent.removeChild(inp);
                    this.parent.label.innerText = this.value;
                    this.parent.label.style.display = 'inline-block';
                    var p = [];
                    p.id = this.parent.id;
                    p.name = this.value;
                    PostAPI('/api/tree/edit/task', p);
                }
            }
        };
        inp.select();
    };
    span.style.display='block';
    childBox.appendChild(span);
    span.id = task.id;
    span.onclick = function(){
        if (event.target == this) {
            showTask(this.id);
        }
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
        for (var i in json.tasks){
            addTask(json.tasks[i]);
        }
    }
}
function addChild(parent){
    console.log('add task for ' + parent);
    var p = [];
    p.parent = parent;
    PostAPI('/api/tree/new/task', p, function(txt){
        console.log(txt);
        addTask(txt);
    });

}
