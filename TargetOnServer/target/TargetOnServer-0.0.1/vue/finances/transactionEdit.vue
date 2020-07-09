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
            date:new Date().toISOString(),
            accountFrom:null,
            accountTo:null,
            counterparty:null,
            amount:0,
            currency:null,
            rate:1,
            comment:''
        }
    }
});