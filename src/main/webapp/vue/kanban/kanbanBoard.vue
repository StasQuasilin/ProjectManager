var board = {
    props:{
        color:String,
        items:Array,
        title:String,
        status:String,
        props:Object
    },
    methods:{
        add:function(item){
            if (this.props){
                this.props.onSave(this.items[item.newIndex],  this.status);
            }

        },
        drag:function(item){
            let e = window.event;
            if (item.isGroup){
                e.preventDefault();
                return false;
            }
        }
    },
    template:'<div >' +
            '<table class="board">' +
                '<tr>' +
                    '<td class="board-title-holder">' +
                        '<div class="board-title">' +
                            '{{props.title}}' +
                        '</div>' +
                    '</td>' +
                '</tr>' +
                '<tr>' +
                    '<td class="board-content-holder">' +
                        '<draggable class="board-content" :list="items" group="task" style="border: solid grey 1pt;" @add="add">' +
                            '<div class="border-item" v-for="task in items" v-on:dragstart="drag(task)" :key="task.id">' +
                                '<div class="border-item-path">' +
                                    '<span v-for="(p, pIdx) in task.path">' +
                                        '<span>' +
                                            '{{p.title}}' +
                                        '</span>' +
                                        '<span v-if="pIdx < task.path.length - 1">-</span>' +
                                    '</span>' +
                                '</div>' +
                                '<div>' +
                                    '{{task.title}}' +
                                '</div>' +
                            '</div>' +
                        '</draggable>' +
                    '</td>' +
                '</tr>' +
            '</table>' +

        '</div>'
};