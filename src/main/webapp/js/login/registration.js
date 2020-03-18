/**
 * Created by quasilin on 22.02.2019.
 */
function registration(){
    var email = document.getElementById('login');
    var password = document.getElementById('password');
    var alert = document.getElementById('alert');

    if (email && password){
        var parameters = [];
        parameters.email = email.value;
        parameters.password = toBase64(password.value);
        var xhr = PostAPI('/a/registration', parameters);
        xhr.onreadystatechange = function(){
            if (xhr.readyState == 4 && xhr.status == 200){
                try {
                    var answer = JSON.parse(xhr.responseText);
                    if (answer){
                        if (answer.status == 'success') {
                            location.href = ctx + '/home.j';
                        } else {
                            alert.innerText = answer.msg;
                        }
                    }
                } catch (e){
                    console.log(xhr.responseText);
                }
            }
        }
    }
}