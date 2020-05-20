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
        '<div>' +
            '<span v-if="item.category">' +
                '{{item.category.name}}: ' +
            '</span>' +
            '<span v-else>' +
                'Without category: ' +
            '</span>' +
            '<span>' +
                '{{item.sum.toLocaleString()}} ' +
            '</span>' +
            '<span v-if="item.currency">' +
                '{{item.currency.sign}}' +
            '</span>' +
        '</div>' +
        '<div>' +
            '{{item.account.title}}' +
        '</div>' +
    '</div>'
};