var itemView = {
    props:{
        item:Object
    },
    template:'<div v-if="item" class="transaction-list-item">' +
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
                    ' ( {{(Math.abs(item.sum) * item.rate).toLocaleString()}} ' +
                    '{{item.budget.currency}} )' +
                '</span>' +
            '</div>' +
            '<div class="account">' +
                '{{item.budget.title}}' +
            '</div>' +
        '</div>'
};