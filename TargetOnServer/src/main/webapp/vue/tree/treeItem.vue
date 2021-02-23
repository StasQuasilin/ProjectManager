treeItem = {
    props:{
        item:Object,
        onclick:Function
    },
    data:function(){
        return {
            show:true
        }
    },
    methods:{
        click:function () {
            if(typeof this.onclick !== "function"){
                console.warn('Function \'onClick\' now allowed!');
            } else {
                this.onclick(this.item.id);
            }
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
                    '<a v-on:click="click()">' +
                        '{{item.title}}' +
                    '</a>' +
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
                    '<tree-item v-for="child in item.children" :onclick="onclick" :key="child.id" :item="child"></tree-item>' +
                '</div>' +
            '</template>' +
        '</div>'
};