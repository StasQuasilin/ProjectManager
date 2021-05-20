itemView = {
    props:{
        item:Object,
        show:Array
    },
    template:'<div class="custom-data-list-item" v-on:click="putValue(item)">' +
            '<template v-if="show !== null">' +
                '<span v-for="s in show">' +
                    '{{item[s]}}' +
                '</span>' +
            '</template>' +
            '<span v-else>' +
                '{{item}}' +
            '</span>' +
        '</div>'
};