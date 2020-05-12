var registration = new Vue({
    el:'#registrator',
    data:{
        api:{},
        languages:[],
        email:'stasvasilin@gmail.com',
        password:'Stas53721456',
        language:'',
        surname:'Василина',
        forename:'Станіслав',
        errors:{
            email:false,
            password:false
        }
    },
    methods:{
        registration:function(){
            console.log('registration');
            this.valid();
            let data = {
                email:this.email,
                password:btoa(this.password),
                language:this.language,
                surname:this.surname,
                forename:this.forename
            };
            console.log(data);
            PostApi(this.api.registration, data, function(a){
                console.log(a);
            })
        },
        valid:function(){
            let e = this.errors;
            console.log('Email: ' + this.email);
            console.log('Password Length: ' + this.password.length );
            //e.email = this.email !== '';
            //e.password = this.password.length > 6;
            //return !e.email && !e.password;
        }
    }
});