transactionEdit = new Vue({
    el:'#transactionEdit',
    components:{
        'input-search':inputSearch
    },
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
            accountFrom:{
                id:-1
            },
            accountTo:{
                id:-1
            },
            counterparty:null,
            amount:0,
            currency:null,
            rate:1,
            comment:''
        },
        props:{
            put:function(category){
                console.log('Put category : ' + category.title);
                transactionEdit.transaction.category = category;
            }
        },
        locale:'uk',
        editNote:false
    },
    methods:{
        save:function(){
            let transaction = Object.assign({}, this.transaction);
            if (transaction.type === 'transfer'){
                delete transaction.category;
            }
            if (transaction.accountFrom){
                transaction.accountFrom = transaction.accountFrom.id;
            }
            if (transaction.accountTo){
                transaction.accountTo = transaction.accountTo.id;
            }
            PostApi(this.api.save, transaction, function(a){
                if (a.status === 'success'){
                    closeModal();
                }
            })
        },
        parseFloat:function(val){
            return parseFloat(val.replaceAll(',', '.'));
        },
        checkField:function(name){
            let value = this.transaction[name];
            let cont = true;
            if (typeof value === "string") {
                let last = value.substring(value.length - 1);
                cont = last !== '.' && last !==',';
            }
            if (cont){
                let num = parseFloat(value);
                if (isNaN(num)){
                    num = 0;
                }
                this.transaction[name] = num;
            }

        },
        remove:function(){
            if (this.api.remove){
                closeModal();
                loadModal(this.api.remove, {id: this.transaction.id})
            }
        }
    }
});