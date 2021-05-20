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
            showDependency:false,
            saveProgressTimer:-1,
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
        fileList:function(){
            if (this.props.fileList);{
                loadModal(this.props.fileList, {task:this.item.id});
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
            let parent = (typeof this.item.header === "undefined") ? this.item.id : this.item.header;
            loadModal(this.props.edit, {parent:parent});
        },
        deleteItem:function () {
            loadModal(this.props.delete, {id:this.item.id});
        },
        sortedChildren:function () {
            return Object.values(this.item.children).sort(function (a, b) {
                return a.title.localeCompare(b.title);
            })
        },
        changeProgress:function(val){
            let p = this.item.progress = parseFloat(this.item.progress)+val;
            this.saveProgress(p);
        },
        saveProgress:function(val){
            clearTimeout(this.saveProgressTimer);
            const self = this;
            this.saveProgressTimer = setTimeout(function () {
                PostApi(self.props.taskProgress, {task:self.item.id,progress:val},function (a) {
                    if(a.status === 'success'){
                        self.item.progress = a.result;
                    }
                })
            }, 1500);
        },
        showDependencyList:function () {
            if(this.item.dependencyCount > 0) {
                this.showDependency = true;
                const self = this;
                PostApi(this.props.dependencyList, {id: this.item.id}, function (a) {
                    if (a.status === 'success') {
                        self.dependencyList = a.result;
                    }
                })
            }
        },
        taskProgressInputStyle:function () {
            return {
                width:(this.item.progress.toString().length + 1) * 6 + 'px'
            }
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
                    '<a style="position: relative" :class="{\'task-impossible\' : item.status === \'impossible\'}" @mouseover="showDependencyList()" @mouseleave="showDependency=false">' +
                        '<div v-if="item.dependencyCount > 0 && dependencyList.length > 0 && showDependency"  class="tooltip">' +
                            '{{item.title}} depend from :' +
                            '<ul>'+
                                '<li v-for="d in dependencyList">' +
                                    '<template v-if="d.status === \'done\'">' +
                                        '&checkmark; ' +
                                    '</template>' +
                                    '<template v-else>' +
                                        '- ' +
                                    '</template>' +
                                    '{{d.title}}' +
                                '</li>' +
                            '</ul>' +
                        '</div>' +
                        '<span v-if="childrenCount === 0" style="padding-left: 4pt;">&nbsp;</span>' +
                        '<span style="display: inline-block; width: 10pt">' +
                            '<template v-if="item.status === \'active\'">' +
                                ' ' +
                            '</template>' +
                            '<span v-else-if="item.status === \'progressing\'" style="color: forestgreen">' +
                                '&#9654;' +
                            '</span>' +
                            '<span v-else-if="item.status === \'done\'" style="color: \'-var(--secondary-color, forestgreen)\'">' +
                                '&checkmark;' +
                            '</span>' +
                            '<span v-else-if="item.status === \'impossible\'">' +
                                '&times; ' +
                            '</span>' +
                            '<span v-else-if="item.status === \'pause\'">' +
                                '&#9208;' +
                            '</span>' +
                            '<span v-else :title="item.status">' +
                                '?' +
                            '</span>' +
                        '</span>' +
                        '<span :class="{\'task-done\' : item.status === \'done\'}" v-on:click="click()">' +
                            '{{item.title}}' +
                        '</span>' +
                        '<template v-if="item.type === \'accumulative\'">' +
                            ' ( ' +
                                '<template v-if="item.status !== \'done\'">' +
                                    '<span class="text-button mini-text-button" v-on:click="changeProgress(-1)">' +
                                        '-' +
                                    '</span>' +
                                    ' <input style="width: 0" :style="taskProgressInputStyle()" v-on:change="changeProgress(0)" onfocus="this.select()" ondblclick="this.select()" ' +
                                        'v-model="item.progress" autocompete="off"> ' +
                                    '<span class="text-button mini-text-button" v-on:click="changeProgress(1)">' +
                                        '+' +
                                    '</span>' +
                                '</template>' +
                                '<template v-else>' +
                                    '{{item.progress.toLocaleString()}}' +
                                '</template>' +
                            ' / {{item.target.toLocaleString()}} )' +
                        '</template>' +
                        '<span v-if="item.deadline">' +
                            'Deadline: {{new Date(item.deadline).toLocaleDateString()}}' +
                        '</span>' +
                    '</a>' +
                    '<div class="tree-menu">' +
                        '<span class="tree-menu-button" v-on:click="addItem()">' +
                            '&#43;' +
                        '</span>' +
                        '<span class="tree-menu-button" v-on:click="fileList()">' +
                            '&#128193;' +
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
                    '<progress-bar  v-if="item.doneIf && item.statistic" :size="item.statistic.active+item.statistic.progressing+item.statistic.done+item.statistic.other" ' +
                        ':value="item.statistic.done" :color="\'green\'"></progress-bar>' +
                    '<div class="doer-bar" v-if="item.doers">' +
                        'Doers: {{item.doers.length.toLocaleString()}}' +
                        '<span class="doer-icon" v-for="doer in item.doers" :title="doer.surname + \' \' + doer.forename">' +
                            '{{doer.surname.substring(0,1)}}{{doer.forename.substring(0,1)}}' +
                        '</span>' +
                    '</div>' +
                '</div>' +
                '<div v-if="childrenCount > 0 && show" class="tree-children">' +
                    '<tree-item v-for="child in sortedChildren()" :onclick="onclick" :key="child.id" :item="child" :props="props"></tree-item>' +
                '</div>' +
            '</template>' +
        '</div>'
};