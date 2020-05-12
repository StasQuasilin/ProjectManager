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