/**
 * Created by szpt_user045 on 22.02.2019.
 */
function login() {
    var loginInp = document.getElementById('login');
    var passwordInp = document.getElementById('password');
    var alert = document.getElementById('alert');

    if (valid(loginInp) & valid(passwordInp)){
        var parameters = [];
        parameters.login = loginInp.value;
        parameters.password = toBase64(passwordInp.value);
        var xhr = PostAPI('/api/login', parameters);
        xhr.onreadystatechange = function() {
            if(xhr.readyState == 4 && xhr.status == 200){
                var answer = JSON.parse(xhr.responseText);
                if(answer){
                    if (answer.status == 'success'){
                        location.href=ctx + '/home.j';
                    } else {
                        alert.innerText = answer.msg;
                    }
                }
            }
        }
    }
}