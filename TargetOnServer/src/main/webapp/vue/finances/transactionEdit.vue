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
                if (category.currency && !transactionEdit.transaction.currency){
                    transactionEdit.transaction.currency = category.currency;
                }
            }
        },
        locale:'uk',
        editNote:false
    },
    methods:{
        save:function(){
            let transaction = Object.assign({}, this.transaction);

            if (transaction.type === 'spending'){
                delete transaction.accountTo;
            } else if (transaction.type === 'income'){
                delete transaction.accountFrom;
            } else if (transaction.type === 'transfer'){
                delete transaction.category;
            }

            PostApi(this.api.save, transaction, function(a){
                if (a.status === 'success'){
                    closeModal();
                }
            })
        },
        changeType:function(type){
            if (this.transaction.type === 'transfer'){
                this.transaction.category = Object.assign({}, {id:-1, title:''});
            }
            if(type === 'income'){
                this.transaction.accountTo = this.transaction.accountFrom;
                this.transaction.accountFrom = -1;
            } else if (type === 'spending'){
                this.transaction.accountFrom = this.transaction.accountTo;
                this.transaction.accountTo = -1;
            } else if (type === 'transfer'){
                console.log(this.transaction);
                if(this.transaction.type === 'income'){
                    this.transaction.accountFrom = this.transaction.accountTo;
                }
                this.transaction.accountTo = this.accounts[1];
                let accounts = this.getAccountsWithout(this.transaction.accountFrom);
                console.log(accounts);
                // if (accounts.length > 0){
                //     this.transaction.accountTo = accounts[1];
                // } else {
                //     this.transaction.accountTo = accounts[0];
                // }
            }
            this.transaction.type = type;
        },
        getAccountsWithout:function(account){
            let accounts = this.accounts.filter(function (item) {
                return item.id !== account.id;
            });
            if (accounts.length === 0){
                return this.accounts;
            } else {
                return accounts;
            }
        },
        showRate:function(){
            let type = this.transaction.type;
            let accountFrom = this.transaction.accountFrom;
            let accountTo = this.transaction.accountTo;
            let currency = this.transaction.currency;
            if (type === 'income'){
                return currency !== accountTo.currency;
            } else if (type === 'spending'){
                return currency !== accountFrom.currency;
            } else {
                return accountFrom.currency !== accountTo.currency;
            }
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