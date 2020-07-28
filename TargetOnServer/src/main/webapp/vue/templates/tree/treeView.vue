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
            '<span class="text-button" :class="item.status" v-on:click="open()" style="font-size: 10pt">' +
                '{{item.title}}' +
            '</span>' +
            '<div style="padding-left: 18px; border-left: dotted 1px gray; " v-if="isOpen && item.children">' +
                '<tree-view v-for="c in item.children" :item="c" :props="props"></tree-view>' +
            '</div>' +
        '</div>'
};