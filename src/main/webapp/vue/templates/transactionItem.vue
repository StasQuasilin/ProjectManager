var itemView = {
    props:{
        item:Object,
        edit:Function
    },
    methods:{

    },
    template:'<div v-if="item" class="transaction-list-item" v-on:click="edit(item)">' +
            '<div>' +
                '<span>' +
                    '{{item.category.name}}: ' +
                '</span>' +
                '<span v-if="item.type===\'income\'">' +
                    '+' +
                '</span>' +
                '<span v-else-if="item.type === \'outcome\'">' +
                    '-' +
                '</span>' +
                '<span>' +
                    '{{Math.abs(item.sum).toLocaleString()}}' +
                '</span>' +
                '<span v-if="item.currency">' +
                    ' {{item.currency.sign}}' +
                '</span>' +
                '<span v-if="item.rate !== 1">' +
                    ' ( &times; {{item.rate.toLocaleString()}} = ' +
                    '{{(Math.abs(item.sum) * item.rate).toLocaleString()}} ' +
                    '{{item.budget.currency}} )' +
                '</span>' +
            '</div>' +
            '<div class="account">' +
                '{{item.budget.title}}' +
                '<span v-if="item.counterparty">' +
                    ' - {{item.counterparty.name}}' +
                '</span>' +
            '</div>' +
            '<div class="transaction-comment">' +
                '{{item.comment}}' +
            '</div>' +
        '</div>'
};