transactionEdit = new Vue({
    el:'#transactionEdit',
    data:{
        api:{},
        types:[],
        accounts:[],
        currency:[],
        transaction:{
            id:-1,
            type:null,
            date:new Date().toISOString().substring(0, 10),
            category:{},
            accountFrom:null,
            accountTo:null,
            counterparty:null,
            amount:0,
            currency:null,
            rate:1,
            comment:''
        }
    },
    methods:{
        save:function(){
            PostApi(this.api.save, this.transaction, function(a){
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});