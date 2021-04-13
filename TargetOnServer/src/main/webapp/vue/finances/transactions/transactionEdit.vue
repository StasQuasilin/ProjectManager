transactionEdit = new Vue({
    el:'#transactionEdit',
    components:{
        'category-input':categoryInput
    },
    data:{
        api:{},
        types:[],
        accounts:[],
        currency:[],
        useDetails:false,
        manyDetails:false,
        editCounterparty:false,
        addCurrency:false,
        newCurrency:{
            title:''
        },
        transaction:{
            id:-1,
            type:null,
            date:new Date().toISOString().substring(0, 10),
            accountFrom:-1,
            accountTo:-1,
            counterparty:{
                id:-1,
                title:''
            },
            amount:0,
            currency:null,
            rate:1,
            comment:'',
            details:[]
        },
        currentDetail:-1,
        category:{
            id:-1,
            title:''
        },
        tempCategory:{},
        showTempCategory:false,
        newCurrencyProps:{
            put:function (currency) {
                transactionEdit.addCurrency = false;
                transactionEdit.newCurrency.title = '';
                transactionEdit.currency.push(currency);
                transactionEdit.transaction.currency = currency;
            }
        },
        props:{
            put:function(header){
                console.log(header);
                let detail;
                if (transactionEdit.currentDetail === -1){
                    detail = {
                        id:-1,
                        header:header.id,
                        title:header.title,
                        amount:1,
                        price:0
                    };
                    if(header.path){
                        detail.path = header.path;
                    }
                    transactionEdit.transaction.details.push(detail);
                } else {
                    detail = transactionEdit.transaction.details[transactionEdit.currentDetail];
                    detail.header = header.id;
                    detail.title = header.title;
                }
                transactionEdit.currentDetail = -1;
                transactionEdit.category.id = -1;
                transactionEdit.category.title ='';
                transactionEdit.updateView();
            }
        },
        totalSum:function(){
            let sum = 0;
            for (let i in this.transaction.details){
                if (this.transaction.details.hasOwnProperty(i)){
                    let detail = this.transaction.details[i];
                    sum += detail.amount * detail.price;
                }
            }
            return sum;
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
        counterpartyProps:{
            put:function(item){
                transactionEdit.transaction.counterparty = item;
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
        detailTree:function(){
            let tree = {};
            for (let i in this.transaction.details){
                if(this.transaction.details.hasOwnProperty(i)){
                    let detail = this.transaction.details[i];
                    if (detail.path){
                        let path = detail.path;
                    } else {

                    }
                }
            }
            return tree;
        },
        changeAmount:function(idx, amount){
            this.transaction.details[idx].amount+=amount;
            this.updateView();
        },
        updateView:function(){
            this.$forceUpdate();
        },
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
        removeDetail:function(idx){
            this.transaction.details.splice(idx, 1);
            this.updateView();
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
        editDetail:function(idx){
            console.log('Edit detail ' + idx);
            this.currentDetail = idx;
            let detail = this.transaction.details[idx];
            this.category = detail.category;
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