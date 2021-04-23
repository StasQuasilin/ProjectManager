accounts = new Vue({
    el:'#accountsList',
    mixins:[list],
    computed:{
        totalBalance:function () {
            let balance = {};
            let items = this.getItems();
            for (let i in items){
                if (items.hasOwnProperty(i)){
                    let item = items[i]; //account
                    let currency = item.currency;
                    if(!balance[currency]){
                        balance[currency] = 0;
                    }
                    balance[currency] += item.sum;
                }
            }
            return balance;
        },
        personalBalance:function () {
            let balance = 0;
            let items = this.getItems();
            for (let i in items){
                if (items.hasOwnProperty(i)){
                    let item = items[i];
                    balance += item.sum;
                }
            }
            return balance;
        },
        accountMap:function () {
            let map = {};
            let items = this.getItems();
            for (let i in items){
                if(items.hasOwnProperty(i)){
                    let item =items[i];
                    let type = item.type;
                    if (!map[type]){
                        map[type] = [];
                    }
                    map[type].push(item);
                }
            }
            return map;
        }
    },
    methods:{
        accountExtract:function(accountId){
            loadModal(this.api.extract, {id:accountId});
        },
        afterHandle:function(){
            if (this.getItems().length === 0){
                this.edit();
            }
        }
    }
});