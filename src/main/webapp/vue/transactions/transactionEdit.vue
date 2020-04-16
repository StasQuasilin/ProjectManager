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
        transaction:{
            type:'',
            date:new Date().toISOString().substring(0, 10),
            sum:0,
            rate:1,
            currency:'',
            category:{
                id:-1,
                name:''
            },
            budget:{
                id:-1
            },
            counterparty:{
                id:-1,
                name:''
            },
            comment:''
        },
        locale:'uk',
        props:{},
        counterpartProps:{},
        addCounterparty:false,
        addNote:false
    },
    methods:{
        setCategory:function(category){
            this.transaction.category = category;
        },
        setCounterparty:function(counterparty){
            this.transaction.countrparty = counterparty;
        },
        save:function(){
            let data = Object.assign({}, this.transaction);
            data.budget = data.budget.id;
            if (!data.id){
                data.id = -1;
            }
            if (!this.addCounterparty){
                delete data.counterparty
            }
            PostApi(this.api.save, data, function(a){
                console.log(a);
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});