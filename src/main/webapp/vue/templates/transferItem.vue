var transferView = {
    props: {
        item: Object
    },
    template:'<div class="transaction-list-item">' +
        '<div class="transaction-time">{{new Date(item.date).toLocaleTimeString().substring(0, 5)}}</div>' +
            '<span>' +
                '{{item.secondary.title}}' +
            '</span>' +
            '<span>' +
                ' &#129042; ' +
            '</span>' +
                '{{(item.sum * -1).toLocaleString()}} {{item.currency.id}}' +
            '<span>' +
                ' &#129042; ' +
            '</span>' +
            '<span>' +
                '{{item.account.title}}' +
            '</span>' +
        '</div>'
}