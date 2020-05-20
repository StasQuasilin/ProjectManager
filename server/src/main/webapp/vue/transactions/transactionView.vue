var transactionList = new Vue({
    el:'#transaction',
    components:{
        'payment-view':paymentView,
        'transfer-view':transferView
    },
    mixins:[list],
    date:function(){
        return {
            account:-1
        }
    },
    methods:{
        edit:function(item){
            console.log('edit');
            if (this.api.edit){
                let data = {};
                if (item && item.id){
                    data.id = item.id;
                }
                loadModal(this.api.edit, data);
            }
        },
        getItemsByDate:function(date){
            let items = [];
            for (let i in this.items){
                if (this.items.hasOwnProperty(i)){
                    let item = this.items[i];
                    let itemDate = new Date(item.date).toISOString().substring(0, 10);
                    if (itemDate === date){
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
                    let date = new Date(item.date).toISOString().substring(0, 10);
                    if (!dates.includes(date)){
                        dates.push(date);
                    }
                }
            }
            dates.sort(function (a, b) {
                return new Date(b) -  new Date(a);
            });
            return dates;
        },
        sort:function (a, b) {
            return new Date(a.date) - new Date(b.date);
        }
    }
});