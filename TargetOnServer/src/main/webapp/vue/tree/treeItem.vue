treeItem = {
    props:{
        item:Object,
        onclick:Function,
        props:Object
    },
    computed:{
        childrenCount:function () {
            if (this.item.children){
                return Object.keys(this.item.children).length;
            }
        }
    },
    data:function(){
        return {
            show:true,
            dependencyList:[],
            showDependency:false
        }
    },
    methods:{
        click:function () {
            if(typeof this.onclick !== "function"){
                console.warn('Function \'onClick\' now allowed!');
            } else {
                this.onclick(this.item.id);
            }
        },
        runTimer:function(){
            PostApi(this.props.runTimer, {task:this.item.id}, function (a) {
                console.log(a);
            })
        },
        addItem:function () {
            loadModal(this.props.edit, {parent:this.item.header});
        },
        deleteItem:function () {
            loadModal(this.props.delete, {id:this.item.id});
        },
        sortedChildren:function () {
            return Object.values(this.item.children).sort(function (a, b) {
                return a.title.localeCompare(b.title);
            })
        },
        showDependencyList:function () {
            const self = this;
            PostApi(this.props.dependencyList, {id:this.item.id}, function (a) {
                if(a.status === 'success'){
                    self.dependencyList = a.result;
                    self.showDependency = true;
                }
            })
        }
    },
    template:'<div>' +
            '<template v-if="item">' +
                '<div class="tree-item" :class="{\'tree-parent\' : childrenCount > 0}">' +
                    '<span v-on:click="show=!show" v-if="childrenCount > 0">' +
                        '<template v-if="show">' +
                            '-' +
                        '</template>' +
                        '<template v-else>' +
                            '+' +
                        '</template>' +
                    '</span> ' +
                    '<span v-if="item.status === \'done\'">' +
                        '&checkmark; ' +
                    '</span>' +
                    '<a v-on:click="click()">' +
                        '<template v-if="item.status === \'active\'">' +
                            '>' +
                        '</template>' +
                        '<template v-else-if="item.status === \'progressing\'">' +
                            '-' +
                        '</template>' +
                        '<template v-else-if="item.status === \'done\'">' +
                            '&checkmark;' +
                        '</template>' +
                        '<span v-else :title="item.status">' +
                            '?' +
                        '</span>' +
                        '<span style="position: relative" v-if="item.dependencyCount" @mouseover="showDependencyList()" @mouseleave="showDependency=false">' +
                            '{{item.dependencyCount}} ' +
                            '<div class="tooltip" v-if="showDependency">' +
                                '{{item.title}} depend from :' +
                                '<ul>'+
                                    '<li v-for="d in dependencyList">' +
                                        '<template v-if="d.status === \'done\'">' +
                                            '&checkmark;' +
                                        '</template>' +
                                        '<template v-else>' +
                                            '-' +
                                        '</template>' +
                                        '{{d.title}}' +
                                    '</li>' +
                                '</ul>' +
                            '</div>' +
                        '</span>' +
                        '{{item.title}}' +
                        '<span v-if="item.deadline">' +
                            'Deadline: {{new Date(item.deadline).toLocaleDateString()}}' +
                        '</span>' +
                    '</a>' +
                    '<div class="tree-menu">' +
                        '<span class="tree-menu-button" v-on:click="addItem()">' +
                            '&#43;' +
                        '</span>' +
                        '<span class="tree-menu-button" v-on:click="runTimer()">' +
                            '&#9202;' +
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
                        '<span class="tree-menu-button" v-on:click="deleteItem()">' +
                            '&times;' +
                        '</span>' +
                    '</div>' +
                '</div>' +
                '<div v-if="childrenCount > 0 && show" class="tree-children">' +
                    '<tree-item v-for="child in sortedChildren()" :onclick="onclick" :key="child.id" :item="child" :props="props"></tree-item>' +
                '</div>' +
            '</template>' +
        '</div>'
};