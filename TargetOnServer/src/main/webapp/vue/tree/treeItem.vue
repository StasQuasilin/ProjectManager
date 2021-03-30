treeItem = {
    props:{
        item:Object,
        onclick:Function,
        props:Object
    },
    components:{
        'progress-bar':progressBar
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
            if(this.item.id !== this.item.title){
                if(typeof this.onclick !== "function"){
                    console.warn('Function \'onClick\' now allowed!');
                } else {
                    this.onclick(this.item.id);
                }
            }
        },
        runTimer:function(){
            PostApi(this.props.runTimer, {task:this.item.id});
        },
        taskTimer:function(){
            loadModal(this.props.taskTimer, {task:this.item.id});
            event.preventDefault();
        },
        changeStatus:function(status){
            PostApi(this.props.taskStatus, {status:status, task:this.item.id});
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
                    '<a v-on:click="click()">' +
                        '<template v-if="item.status === \'active\'">' +
                            ' ' +
                        '</template>' +
                        '<span v-else-if="item.status === \'progressing\'" style="color: forestgreen">' +
                            '&#9654;' +
                        '</span>' +
                        '<span v-else-if="item.status === \'done\'" style="color: forestgreen">' +
                            '&checkmark;' +
                        '</span>' +
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
                        '<span :class="{\'task-done\' : item.status === \'done\'}">' +
                            '{{item.title}}' +
                        '</span>' +
                        '<span v-if="item.deadline">' +
                            'Deadline: {{new Date(item.deadline).toLocaleDateString()}}' +
                        '</span>' +
                    '</a>' +
                    '<div class="tree-menu">' +
                        '<span class="tree-menu-button" v-on:click="addItem()">' +
                            '&#43;' +
                        '</span>' +
                        '<template v-if="item.status !== \'done\'" >' +
                            '<span class="tree-menu-button" v-on:click="runTimer()" v-on:click.right="taskTimer()">' +
                                '&#9202;' +
                            '</span>' +
                            '<span class="tree-menu-button" v-on:click="changeStatus(\'done\')">' +
                                '&check;' +
                            '</span>' +
                        '</template>' +
                        '<span v-else class="tree-menu-button" v-on:click="changeStatus(\'active\')">' +
                            '&#9205;' +
                        '</span>' +
                        '<span class="tree-menu-button" v-on:click="changeStatus(\'pause\')">' +
                            '&#9208;' +
                        '</span>' +
                        '<span class="tree-menu-button" v-on:click="deleteItem()">' +
                            '&times;' +
                        '</span>' +
                    '</div>' +
                    '<br>' +
                    '<progress-bar  v-if="item.doneIf && item.statistic" :size="item.statistic.active+item.statistic.progressing+item.statistic.done" ' +
                        ':value="item.statistic.done" :color="\'green\'"></progress-bar>' +
                '</div>' +
                '<div v-if="childrenCount > 0 && show" class="tree-children">' +
                    '<tree-item v-for="child in sortedChildren()" :onclick="onclick" :key="child.id" :item="child" :props="props"></tree-item>' +
                '</div>' +
            '</template>' +
        '</div>'
};