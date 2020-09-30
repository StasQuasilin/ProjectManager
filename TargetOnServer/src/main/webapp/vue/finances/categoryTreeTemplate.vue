treeItem = {
    props:{
        item:Object
    },
    methods:{
        details:function () {
            categoryList.getCategories(this.item);
        }
    },
    template:'<div>' +
            '<div class="text-button" v-on:click="details()">' +
                '{{item.title}}' +
            '</div>' +
            '<div v-if="item.details">' +
                '{{item.details}}' +
            '</div>' +
            '<div style="padding-left: 24px">' +
                '<category-tree-item v-for="children in item.categories" :key="children.id" :item="children"></category-tree-item>' +
            '</div>' +
        '</div>'


};