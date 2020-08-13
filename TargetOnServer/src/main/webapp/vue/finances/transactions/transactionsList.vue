transactionsList = new Vue({
    el:'#transactions',
    mixins:[list],
    data:function(){
        return {
            useFilter:false
        }
    },
    computed:{
        compoundItems:function () {
            let compound = {};
            let items = this.getItems();
            for (let i in items){
                if (items.hasOwnProperty(i)){
                    let item = items[i];
                    let date = item.date;
                    if (!compound[date]){
                        compound[date] = [];
                    }
                    compound[date].push(item);
                }
            }
            return compound;
        }
    },
    methods:{
        tryEdit:function(){
            if (accounts.getItems().length === 0){
                accounts.edit();
            } else {
                this.edit();
            }
        },
        sort:function(a, b){
            return new Date(b.date) - new Date(a.date);
        }
    }
});