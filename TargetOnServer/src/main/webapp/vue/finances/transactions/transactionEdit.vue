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
        useDetails:false,
        transaction:{
            id:-1,
            type:null,
            date:new Date().toISOString().substring(0, 10),
            category:{id:-1, title:''},
            accountFrom:-1,
            accountTo:-1,
            counterparty:null,
            amount:0,
            currency:null,
            rate:1,
            comment:'',
            details:[]
        },
        props:{
            put:function(category){
                transactionEdit.transaction.category = category;
                if (category.currency && !transactionEdit.transaction.currency){
                    transactionEdit.transaction.currency = category.currency;
                }
            }
        },
        detailProps:{
            put:function (item) {
                transactionEdit.detail.category = item.id;
                transactionEdit.detail.title = item.title;
                if (item.parent){
                    transactionEdit.detail.parent = item.parent;
                }
            }
        },
        detail:{
            id:-1,
            category:-1,
            title:'',
            amount:1,
            price:0,
            currency:'',
            rate:1
        },
        detailIndex:-1,
        locale:'uk',
        editNote:false,
    },
    computed:{
        accountsMap:function () {
            let accounts = {};
            for (let i in this.accounts) {
                if (this.accounts.hasOwnProperty(i)) {
                    let account = this.accounts[i];
                    accounts[account.id] = account;
                }
            }
            return accounts;
        },
        showRate:function(){
            if (this.transaction) {
                let accounts = this.accountsMap;
                let type = this.transaction.type;
                let currency = this.transaction.currency;
                if (type === 'income') {
                    return currency !== accounts[this.transaction.accountTo].currency;
                } else if (type === 'spending') {
                    return currency !== accounts[this.transaction.accountFrom].currency;
                } else if (type === 'transfer') {
                    return accounts[this.transaction.accountTo].currency !== accounts[this.transaction.accountFrom].currency;
                }
            }
        }
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
                transaction.currency = this.accountsMap[transaction.accountTo].currency;
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

                if(this.transaction.type === 'income'){
                    let accounts = this.getAccountsWithout(this.transaction.accountTo);
                    this.transaction.accountFrom = accounts[0].id;
                //     this.transaction.accountTo = ;
                } else if (this.transaction.type === 'spending'){
                    let accounts = this.getAccountsWithout(this.transaction.accountFrom);
                    this.transaction.accountTo = accounts[0].id;
                }

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
                return item.id !== account;
            });
            if (accounts.length === 0){
                return this.accounts;
            } else {
                return accounts;
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
        },
        deleteDetail:function(index){
            this.transaction.details.splice(index, 1);
            this.calculateTotal();
        },
        editDetail:function(detail, index){
            this.detail = detail;
            this.detailIndex = index;
        },
        addDetail:function(){
            let detail = Object.assign({}, this.detail);
            if (!this.transaction.category.id){
                if (detail.parent){
                    this.transaction.category = detail.parent;
                }
            }
            if (this.detailIndex !== -1){
                this.transaction.details.splice(this.detailIndex, 1, detail);
                this.detailIndex = -1;
            } else {
                this.transaction.details.push(detail);
            }
            this.detail = {
                id:-1,
                title:'',
                amount:1,
                price:0
            };
            this.calculateTotal();
        },
        calculateTotal:function(){
            let total = 0;
            for (let i = 0; i < this.transaction.details.length; i++){
                let d = this.transaction.details[i];
                total += d.amount * d.price
            }
            this.transaction.amount = total;
        },
        invertRate:function(){
            this.transaction.rate = -1;
        }
    }
});