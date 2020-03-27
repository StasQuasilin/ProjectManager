var treeView = {
    props:{
        item:Object,
        props:Object
    },
    methods:{
        select:function(item){
            this.props.onSelect(item);
        }
    },
    template:'<div>' +
            '<div class="tree-view-item">' +
                '<span v-on:click="select(item)">' +
                    '{{item.title}}' +
                '</span>' +
                '<span class="tree-view-item-menu">' +
                    '+' +
                '</span>' +
            '</div>' +

            '<div style="padding-left: 12pt; border-left: dashed gray 1pt">' +
                '<tree-view v-for="child in item.children" :item="child" :props="props" :key="child.id"></tree-view>' +
            '</div>' +
        '</div>'
};