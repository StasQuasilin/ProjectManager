treeItem = {
    props:{
        item:Object
    },
    data:function(){
        return {
            show:true
        }
    },
    template:'<div>' +
            '<template v-if="item">' +
                '<div class="tree-item" :class="{\'tree-parent\' : item.children && item.children.length > 0}">' +
                    '<span v-on:click="show=!show" v-if="item.children && item.children.length > 0">' +
                        '<template v-if="show">' +
                            '-' +
                        '</template>' +
                        '<template v-else>' +
                            '+' +
                        '</template>' +
                    '</span>' +
                    '<span v-if="item.status === \'done\'">' +
                        '&checkmark; ' +
                    '</span>' +
                    '<span>' +
                        '{{item.title}} ( {{item.status}} )' +
                    '</span>' +
                    '<div class="tree-menu">' +
                        '<span class="tree-menu-button">' +
                            '&#43;' +
                        '</span>' +
                        '<span v-if="item.status !== \'done\'" class="tree-menu-button">' +
                            '&check;' +
                        '</span>' +
                        '<span v-else class="tree-menu-button">' +
                            '&#9205;' +
                        '</span>' +
                        '<span class="tree-menu-button">' +
                            '&#9208;' +
                        '</span>' +
                        '<span class="tree-menu-button">' +
                            '&times;' +
                        '</span>' +
                    '</div>' +
                '</div>' +
                '<div v-if="item.children && item.children.length > 0 && show" class="tree-children">' +
                    // '<div v-for="child in item.children">{{child}}</div>' +
                    // '{{item.children}}' +
                    '<tree-item v-for="child in item.children" :key="child.id" :item="child"></tree-item>' +
                '</div>' +
            '</template>' +
        '</div>'
};