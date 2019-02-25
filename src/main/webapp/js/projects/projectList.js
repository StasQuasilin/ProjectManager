/**
 * Created by quasilin on 24.02.2019.
 */
$(document).ready(function(){
    var myProjects = document.getElementById('my-project-container');
    var otherProjects = document.getElementById('other-project-container');
    var xhr = PostAPI('/api/project/list');
    xhr.onreadystatechange = function(){
        if (xhr.readyState == 4 && xhr.status == 200){
            try {
                var answer = JSON.parse(xhr.responseText);

            } catch (e){

            }
        }
    }
});