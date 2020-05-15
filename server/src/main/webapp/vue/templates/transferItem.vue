var transferView = {
    props: {
        item: Object
    },
    template:'<div class="transaction-list-item">' +
        '<div class="transaction-time">{{new Date(item.date).toLocaleTimeString().substring(0, 5)}}</div>' +
            '<span v-if="item.secondary">' +
                '{{item.secondary.title}}' +
            '</span>' +
            '<span v-else>' +
                'No secondary' +
            '</span>' +
            '<span>' +
                ' &#10230; ' +
            '</span>' +
                '{{(item.sum * -1).toLocaleString()}} {{item.currency.id}}' +
            '<span>' +
                ' &#10230; ' +
            '</span>' +
            '<span v-if="item.account">' +
                '{{item.account.title}}' +
            '</span>' +
            '<span v-else>' +
                'No account' +
            '</span>' +
        '</div>'
};
