/**
 * Created by quasilin on 24.02.2019.
 */
$(document).ready(function(){
    var myProjects = document.getElementById('my-project-container');
    var otherProjects = document.getElementById('other-project-container');
    var boxPref = document.getElementById('box');
    var xhr = PostAPI('/api/project/list');
    xhr.onreadystatechange = function(){
        if (xhr.readyState == 4 && xhr.status == 200){
            try {
                var answer = JSON.parse(xhr.responseText);
                for (var j in answer){
                    console.log(answer[j]);
                    var box = boxPref.cloneNode(true);
                    box.style.display='block';
                    myProjects.appendChild(box);
                    getChildElemById(box, 'project-title').innerText = answer[j].title;
                    getChildElemById(box, 'project-begin-date').innerText = dateFormatter(answer[j].beginDate);
                    getChildElemById(box, 'project-complete-date').innerText = dateFormatter(answer[j].completeDate);
                    getChildElemById(box, 'active-tasks').innerText = answer[j].active;
                    getChildElemById(box, 'canceled-tasks').innerText = answer[j].canceled;
                    getChildElemById(box, 'done-tasks').innerText = answer[j].done;
                    getChildElemById(box, 'done-progress-bar').style.width = answer[j].progress + '%';
                    box.id=answer[j].id;

                    box.onclick=function(){
                        location.href=ctx + '/tree.j?project='+this.id;
                    }
                }
            } catch (e){

            }
        }
    }
});