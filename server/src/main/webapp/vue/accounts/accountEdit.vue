var edit = new Vue({
    el:'#editor',
    data:{
        api:{},
        types:[],
        account:{
            id:-1,
            title:'',
            type:-1,
            amount:0,
            limit:0,
            currency:-1
        },
        depositSettings:{
            open:null,
            close:null,
            bid:12,
            payment:1,
            account:0
        },
        accounts:[],
        currency:[],
        months:{},
        date1:false,
        date2:false
    },
    mounted:function(){
        this.depositSettings.open = this.nowPlusMonth().toISOString().substring(0, 10);
        this.depositSettings.close = this.nowPlusMonth(6).toISOString().substring(0, 10);
    },
    methods:{
        nowPlusMonth:function(month){
            let now = new Date();
            if (month){
                now.setMonth(now.getMonth() + month);
            }
            return now;
        },
        save:function(){
            let account = Object.assign({}, this.account);
            if (account.type === 'deposit'){
                account.settings = Object.assign({}, this.depositSettings);
            }
            PostApi(this.api.save, account, function(a){
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});