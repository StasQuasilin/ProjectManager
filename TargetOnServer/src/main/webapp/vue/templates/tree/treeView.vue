treeView = {
    props:{
        item:Object,
        props:Object,
        current:Number
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
    template:'<div v-if="item">' +
            '<span class="text-button" v-on:click="isOpen=!isOpen" style="padding-right: 4px">' +
                '<template v-if="isOpen">' +
                    '-' +
                '</template>' +
                '<template v-else>' +
                    '+' +
                '</template>' +
            '</span>' +
            '<span class="text-button" :class="{selected : item.category === current}" v-on:click="open()" style="font-size: 10pt">' +
                '{{item.title}}' +
            '</span>' +
            '<div style="padding-left: 18px; border-left: dotted 1px gray; " v-if="isOpen && item.children">' +
                '<tree-view v-for="c in item.children" :key="c.id" :item="c" :props="props" :current="current"></tree-view>' +
            '</div>' +
        '</div>'
};