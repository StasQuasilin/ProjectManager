/**
 * Created by quasilin on 24.02.2019.
 */
function saveProject(){
    var id = document.getElementById('id');
    var title = document.getElementById('title');
    var date = document.getElementById('date');
    var description = document.getElementById('description');
    if (valid(title)){
        var p = [];
        if (id.value){
            p.id = id.value;
        }
        p.title = title.value;
        if (date.value){
            p.date = date.value;
        }
        if (description.value){
            p.description = description.value;
        }
        var xhr = PostAPI('/api/project/save', p);
        xhr.onreadystatechange = function(){
            if (xhr.readyState == 4 && xhr.status == 200){

            }
        }
    }
}
