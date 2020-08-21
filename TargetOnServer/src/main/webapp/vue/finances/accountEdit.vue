accountEdit = new Vue({
    el:'#accountEdit',
    components:{
        'date-picker':datePicker
    },
    data:{
        api:{},
        types:[],
        typeNames:{},
        currency:[],
        account:{
            id:-1,
            title:'',
            type:null,
            sum:0,
            currency:null,
            limit:0
        },
        depositDateProps:{
            put:function (date) {
                Vue.set(accountEdit.account.settings, 'open', date);
            }
        }
    },
    methods:{
        datePlusDays:function(date, days){
            let open = new Date(date);
            open.setDate(open.getDate() + days);
            return open;
        },
        save:function(){
            let account = Object.assign({}, this.account);
            if (account.type === 'credit'){
                account.amount = 0;
            }
            PostApi(this.api.save, account, function(a){
                if(a.status === 'success'){
                    closeModal();
                }
            })
        },
        sort:function(a, b){
            return a.title.localeCompare(b.title);
        },
        checkType:function () {
            let account = this.account;
            if (account.type === 'card'){
                account.settings = {
                    exemption:0,
                    remember:true
                }
            } else if (account.type === 'deposit'){
                account.settings = {
                    open:new Date().toISOString().substring(0, 10),
                    period:30,
                    rate:12,
                    capitalization:false
                }
            }
        }
    }
});