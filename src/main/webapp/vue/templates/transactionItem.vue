var itemView = {
    props:{
        item:Object
    },
    template:'<div v-if="item">' +
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
            '</div>' +
            '<div>' +
                '{{item.budget.title}}' +
            '</div>' +
        '</div>'
};