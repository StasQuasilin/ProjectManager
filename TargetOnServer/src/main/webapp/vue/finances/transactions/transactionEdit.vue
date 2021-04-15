transactionEdit = new Vue({
    el:'#transactionEdit',
    components:{
        'category-input':categoryInput,
        'input-search':inputSearch
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
        rateInfo:{
            base:'',
            currency:'',
            rate:0
        }
    },
    computed:{
        totalSum:function(){
            if(this.transaction.type === 'transfer'){
                return this.transaction.amount;
            } else {
                let sum = 0;
                for (let i in this.transaction.details){
                    if (this.transaction.details.hasOwnProperty(i)){
                        let detail = this.transaction.details[i];
                        sum += detail.amount * detail.price;
                    }
                }
                return sum;
            }
        },
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
            let c;
            if (this.transaction) {
                let accounts = this.accountsMap;
                let type = this.transaction.type;
                let currency = this.transaction.currency;
                if (type === 'income') {
                    this.rateInfo.base = currency;
                    this.rateInfo.currency = accounts[this.transaction.accountTo].currency;
                } else if (type === 'spending') {
                    this.rateInfo.base = currency;
                    this.rateInfo.currency = accounts[this.transaction.accountFrom].currency;
                } else if (type === 'transfer') {
                    this.rateInfo.currency = accounts[this.transaction.accountTo].currency;
                    this.rateInfo.base = accounts[this.transaction.accountFrom].currency;
                }
                let number = this.rateInfo.base.localeCompare(this.rateInfo.currency);
                if(number){
                    this.rateReq();
                } else {
                    this.transaction.rate = 1;
                }
                return number;
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
        rateReq:function () {
            if(this.transaction.rate === 1) {
                const self = this;
                PostApi(this.api.rate, this.rateInfo, function (a) {
                    if (a.status === 'success') {
                        let result = a.result;
                        let base = result['base_code'];
                        let currency = result['target_code'];
                        let rate = result['conversion_rate'];
                        let info = self.rateInfo;
                        self.transaction.rate = rate;
                        if (info.base.localeCompare(base) && info.currency.localeCompare(currency)) {
                            info.rate = rate;
                            self.transaction.rate = rate;
                            // if(self.transaction.rate !== 1){
                            //     self.transaction.rate = rate;
                            // }
                        }
                        console.log(base + '/' + currency + '=' + rate);
                    }
                })
            }
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
        initTransaction:function(){
            transactionEdit.transaction.type = transactionEdit.types[0].id;

            let currency = transactionEdit.transaction.currency = transactionEdit.currency[0];
            let accId = 0;
            for (let i in this.accounts){
                if (this.accounts.hasOwnProperty(i)){
                    let account = this.accounts[i];
                    if(!account.currency.localeCompare(currency)){
                        accId = i;
                        break;
                    }
                }
            }
            transactionEdit.transaction.accountFrom = transactionEdit.accounts[accId].id;
        },
        changeType:function(type){
            // if (this.transaction.type === 'transfer'){
            //     this.transaction.category = Object.assign({}, {id:-1, title:''});
            // }
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