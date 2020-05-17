var edit = new Vue({
    components:{
        'find':findInput
    },
    el:'#editor',
    data:{
        api:{},
        types:[],
        budgets:[],
        currencyList:[],
        selectDate:true,
        transaction:{
            type:'',
            date:new Date().toISOString(),
            sum:0,
            rate:1,
            currency:'',
            category:{
                id:-1,
                name:''
            },
            account:{
                id:-1
            },
            secondary:{
                id:-1
            },
            counterparty:{
                id:-1,
                name:''
            },
            details:[],
            comment:''
        },
        locale:'uk',
        props:{},
        counterpartProps:{},
        addCounterparty:false,
        addNote:false,
        addDetails:false,
        showDetails:false
    },
    methods:{
        closeDetails:function(){
            this.addDetails = false;
            this.transaction.details = [];
        },
        setCategory:function(category){
            this.transaction.category = category;
        },
        setCounterparty:function(counterparty){
            this.transaction.countrparty = counterparty;
        },
        save:function(){
            let data = Object.assign({}, this.transaction);

            data.account = data.account.id;
            if (!data.id){
                data.id = -1;
            }
            if (!this.addCounterparty){
                delete data.counterparty
            }
            if (data.type !== 'transfer'){
                delete data.secondary;
            } else {
                data.secondary = data.secondary.id;
                delete data.category;
                delete data.details;
            }
            console.log(data);
            PostApi(this.api.save, data, function(a){
                console.log(a);
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});