calendarFreeItem = {
    props:{
        item:Object
    },
    template:'<div style="border: solid gray 1pt; padding: 2px; margin: 2px">' +
            '<div v-if="item.parent" style="font-size: 8pt">' +
                '<span v-for="(p, pIdx) in item.parent.path">' +
                    '{{p.title}} / ' +
                '</span>' +
                '<span>' +
                    '{{item.parent.title}}' +
                '</span>' +
            ' </div>' +
            ' <div>' +
                '<template v-if="item.date">' +
                    '{{item.date}}' +
                '</template>' +
                '<template v-if="item.time">' +
                    '{{item.time}}' +
                '</template>' +

                '{{item.title}}' +
            ' </div>' +
        '</div>'
};