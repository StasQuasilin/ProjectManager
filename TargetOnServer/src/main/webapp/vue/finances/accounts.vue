accounts = new Vue({
    el:'#accountsList',
    mixins:[list],
    computed:{
        totalBalance:function () {
            let balance = 0;
            let items = this.getItems();
            for (let i in items){
                if (items.hasOwnProperty(i)){
                    let item = items[i];
                    balance += item.limit + item.sum;
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