var accounts = new Vue({
    el:'#accounts',
    mixins:[list],
    methods:{
        totalAmount:function(){
            let total = {};
            for (let i in this.items){
                if (this.items.hasOwnProperty(i)){
                    let item = this.items[i];
                    let currency = item.currency.id;
                    if (!total[currency]){
                        total[currency] = 0;
                    }
                    total[currency] += item.amount;
                }
            }
            return total;
        }
    }
});