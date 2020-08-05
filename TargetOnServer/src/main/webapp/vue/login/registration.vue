registration = new Vue({
    el:'#registration',
    data:{
        api:{},
        locale:'',
        locales:[],
        localeNames:{},
        password:'',
        registrationData:{
            email:'',
            password:'',
            surname:'',
            forename:''
        },
        errors:{
            email:false,
            password:false,
            surname:false
        },
        message:''
    },
    methods:{
        isValid:function(){
            let registration = this.registrationData;
            let errors = this.errors;
            let valid = true;
            if (registration.email.length < 5){
                errors.email = true;
                valid = false;
            }
            if (registration.password.length < 5 || registration.password !== this.password){
                errors.password = true;
                valid = false;
            }
            if (registration.surname.length + registration.forename.length < 2){
                errors.surname = true;
                valid = false;
            }
            return valid;
        },
        registration:function () {
            if (this.isValid()){
                let registration = Object.assign({}, this.registrationData);
                registration.password = btoa(registration.password);
                registration.locale = this.locale;
                const self = this;
                PostApi(this.api.registration, registration, function (a) {
                    if (a.status === 'success'){
                        location.href = context + a.redirect
                    } else {
                        self.message = a.message;
                    }
                })
            }
        },
        login:function () {
            location.href = context + this.api.login + '?lang=' + this.locale
        },
        changeLocale:function () {
            location.href = (location.pathname + '?lang=' + this.locale);
        }
    }
});