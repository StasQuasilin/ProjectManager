var treeView = {
    props:{
        item:Object,
        props:Object
    },
    data:function(){
        return {
            active:'active',
            progressing:'progressing',
            done:'done'
        }
    },
    methods:{
        select:function(){
            this.props.onSelect(this.item);
        },
        add:function () {
            console.log('parent ' + this.item.id);
        }
    },
    template:'<div>' +
            '<div class="tree-view-item">' +
                '<span v-on:click="select()">' +
                    '<span v-if="item.children && item.children.length > 0">' +
                        '<img style="width: 12pt" src="img/buttons/folder.svg" alt="">' +
                    '</span>' +
                    '<span v-else-if="item.status===active" class="button-holder">' +
                        '<img style="width: 10pt" src="img/buttons/todo.svg" alt="">' +
                    '</span>' +
                    '<span v-else-if="item.status===progressing" class="button-holder">' +
                        '<img style="width: 8pt" src="img/buttons/active.svg" alt="">' +
                    '</span>' +
                    '<span v-else-if="item.status===done" class="button-holder">' +
                        '<img style="width: 8pt" src="img/buttons/done.svg" alt="">' +
                    '</span>' +
                    '<span>' +
                        '{{item.title}}' +
                    '</span>' +
                '</span>' +
                '<div class="tree-view-item-menu">' +
                    '<span v-on:click="add()">+</span>' +
                '</div>' +
            '</div>' +
            '<div style="padding-left: 12pt; border-left: dashed gray 1pt">' +
                '<tree-view v-for="child in item.children" :item="child" ' +
                    ':props="props" :key="child.id"></tree-view>' +
            '</div>' +
        '</div>'
};