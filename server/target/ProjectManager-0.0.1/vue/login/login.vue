var login = new Vue({
    el:'#login',
    data:{
        api:{},
        email:'',
        password:''
    }, methods:{
        login:function(){
            let data = {
                email:this.email,
                password:btoa(this.password)
            };
            PostApi(this.api.login, data, function(a){
                if (a.status === 'success'){
                    window.location = a.redirect;
                }

            })
        }
    }
});