treeView = {
    props:{
        item:Object,
        props:Object
    },
    data:function(){
        return{
            isOpen:true
        }
    },
    methods:{
        open:function(){
            this.props.onOpen(this.item);
        }
    },
    template:'<div>' +
            '<span class="text-button" v-on:click="isOpen=!isOpen" style="padding-right: 4px">' +
                '<template v-if="isOpen">' +
                    '-' +
                '</template>' +
                '<template v-else>' +
                    '+' +
                '</template>' +
            '</span>' +
            '<span class="text-button" v-on:click="open()">' +
                '{{item.title}}' +
            '</span>' +
            '<div style="padding-left: 24px; border-left: dotted 1px gray; " v-if="isOpen && item.children">' +
                '<div v-for="c in item.children">' +
                    '<tree-view :item="c" :props="props"></tree-view>' +
                '</div>' +
            '</div>' +
        '</div>'
}