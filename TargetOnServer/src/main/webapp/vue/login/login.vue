loginApp = new Vue({
    el:'#loginApp',
    data:{
        api:{},
        locale:'',
        locales:[],
        localeNames:{},
        login:{
            email:'',
            password:''
        },
        errors:{
            email:false,
            password:false
        },
        message:''
    },
    methods:{
        isValid:function(){
            let login = this.login;
            let errors = this.errors;
            let valid = true;
            if(login.email.length < 5){
                errors.email = true;
                valid = false;
            }
            if (login.password.length < 5){
                errors.password = true;
                valid = false;
            }
            return valid;
        },
        signIn:function () {
            if (this.isValid()) {
                let login = Object.assign({}, this.login);
                login.password = btoa(login.password);
                login.language = this.locale;
                const self = this;
                PostApi(this.api.login, login, function (a) {
                    if (a.status === 'success'){
                        location.href = context + a.redirect;
                    } else {
                        self.message = a.message;
                    }
                })
            }
        },
        registration:function () {
            location.href = context + this.api.registration + '?lang=' + this.locale
        },
        changeLocale:function () {
            location.href = (location.pathname + '?lang=' + this.locale);
        },
        demo:function () {
            PostApi(this.api.demo, {locale: this.locale}, function (a) {
                if (a.status === 'success'){
                    if (a.redirect){
                        location.href = context + a.redirect;
                    }
                }
            })

        }
    }
});