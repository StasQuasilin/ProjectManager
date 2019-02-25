/**
 * Created by quasilin on 24.02.2019.
 */
function saveProject(){
    var title = document.getElementById('title');


    if (valid(title)){
        var id = document.getElementById('id');
        var begin = document.getElementById('date-begin');
        var complete = document.getElementById('date-complete');
        var description = document.getElementById('description');

        var p = [];
        if (id.value){
            p.id = id.value;
        }
        p.title = title.value;
        if (begin.value){
            p.begin = begin.value;
        }
        if (complete.value){
            p.complete = complete.value;
        }
        if (description.value){
            p.description = description.value;
        }
        var fixedBudget = document.getElementById('budget-fixed');
        var floatBudget = document.getElementById('budget-float');

        p.budgetType = fixedBudget.checked ? 'fixed' : floatBudget.checked ? 'float' : 'none';
        p.budgetSum = document.getElementById('budget-sum').value;
        p.budgetCurrency = document.getElementById('budget-currency').value;

        var xhr = PostAPI('/api/project/save', p);
        xhr.onreadystatechange = function(){
            if (xhr.readyState == 4 && xhr.status == 200){

            }
        }
    }
}
function getProjectLength(){
    var p = [];
    p.begin = document.getElementById('date-begin').value;
    p.complete = document.getElementById('date-complete').value;
    var xhr = PostAPI('/api/info/project/length', p);
    xhr.onreadystatechange = function(){
        if (xhr.readyState == 4 && xhr.status == 200){
            var answer = JSON.parse(xhr.responseText);
            console.log(answer);
            if (answer.status == 'success'){
                document.getElementById('project-length').innerText = answer.length.toLocaleString();
            }
        }
    }
}
function addMonths(count){
    var complete = document.getElementById('date-complete');
    var p = [];
    p.date = complete.value;
    p.count = count;
    console.log(count);
    var xhr = PostAPI('/api/info/plus/month', p);
    xhr.onreadystatechange = function(){
        if (xhr.readyState == 4 && xhr.status == 200){
            complete.value = xhr.responseText;
            getProjectLength();
        }
    }


}
