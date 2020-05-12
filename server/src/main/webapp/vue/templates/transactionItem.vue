var itemView = {
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
                '<span>' +
                    '{{item.details[0].category.name}}' +
                '</span>' +
                '<span v-if="item.details.length > 1">' +
                    '+ {{item.details.length - 1}}' +
                '</span>' +
                '<span>' +
                    ': ' +
                '</span>' +
                '<span v-if="item.type===\'income\'">' +
                    '+' +
                '</span>' +
                '<span v-else-if="item.type === \'outcome\'">' +
                    '-' +
                '</span>' +
                '<span>' +
                    '{{sum.toLocaleString()}}' +
                '</span>' +
                '<span v-if="item.currency">' +
                    ' {{item.currency.sign}}' +
                '</span>' +
                '<span v-if="item.rate !== 1">' +
                    ' ( &times; {{item.rate.toLocaleString()}} = ' +
                    '{{(Math.abs(sum) * item.rate).toLocaleString()}} ' +
                    '{{item.details[0].account.currency}} )' +
                '</span>' +
            '</div>' +
            '<div class="account">' +
                '{{item.details[0].account.title}}' +
                '<span v-if="item.counterparty">' +
                    ' - {{item.counterparty.name}}' +
                '</span>' +
            '</div>' +
            '<div class="transaction-comment">' +
                '{{item.comment}}' +
            '</div>' +
        '</div>'
};