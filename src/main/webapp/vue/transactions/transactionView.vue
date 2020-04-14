var transactionList = new Vue({
    el:'#transaction',
    components:{
        'item-view':itemView
    },
    mixins:[list],
    date:function(){
        return {
            account:-1
        }
    },
    methods:{
        getItemsByDate:function(date){
            let items = [];
            for (let i in this.items){
                if (this.items.hasOwnProperty(i)){
                    let item = this.items[i];
                    if (item.date === date){
                        items.push(item);
                    }
                }
            }
            return items;
        },
        getDates:function(){
            let dates = [];
            for (let i in this.items){
                if (this.items.hasOwnProperty(i)){
                    let item = this.items[i];
                    let date = item.date;
                    if (!dates.includes(date)){
                        dates.push(date);
                    }
                }
            }
            dates.sort(function (a, b) {
                return new Date(b) -  new Date(a);
            })
            return dates;
        }
    }
})