var paymentView = {
    props:{
        item:Object,
        edit:Function
    },
    computed:{
        sum:function(){
            let sum = 0;
            for(let i in this.item.details){
                if (this.item.details.hasOwnProperty(i)){
                    let detail = this.item.details[i];
                    sum += Math.abs(detail.sum);
                }
            }
            return sum;
        }
    },
    methods:{

    },
    template:'<div v-if="item" class="transaction-list-item" v-on:click="edit(item)">' +
        '<div class="transaction-time">{{new Date(item.date).toLocaleTimeString().substring(0, 5)}}</div>' +

        '<div>' +
            '{{item}}' +
        '</div>' +
    '</div>'
};