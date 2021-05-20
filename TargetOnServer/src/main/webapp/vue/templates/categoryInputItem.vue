itemView = {
    props:{
        item:Object,
        show:Array,
        put:Function
    },
    methods:{
        check:function (item) {
            if (typeof this.put === "function"){
                this.put(item);
            } else {
                console.warn('Type of put is ' + typeof this.put);
            }
        }
    },
    template:'<div class="custom-data-list-item">' +
                '<span style="background-color: rgba(151,96,0,0.32)" v-on:click="check(item)">' +
                    '<template v-if="item.path">' +
                        '<span v-for="p in item.path">' +
                            '{{p.title}} / ' +
                        '</span>' +
                    '</template>' +
                    '<span>' +
                        '{{item.title}}' +
                    '</span>' +
                '</span>' +
                '<span class="add-to-path" v-on:click="">' +
                    '/' +
                '</span>' +
        '</div>'
};