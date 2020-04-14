var itemView = {
    props:{
        project:Object,
        props:Object
    },
    methods:{
        onPause:function(){

        },
        onDelete:function(){
            if (this.props.onDelete){
                this.props.onDelete(this.project.id);
            }
        },
        onEdit:function(){
            this.props.onEdit(this.project.id);
        },
        projectLength:function () {
            let begin = new Date(this.project.begin);
            let end = new Date(this.project.end);
            return end - begin;
        },
        timeProgress:function(){
            let begin = new Date(this.project.begin);
            let now = new Date();
            return now - begin;
        }
    },
    template:'<div style="border: solid black 1pt">' +
        '<div class="project-box-title">' +
            '<div style="padding: 0 8pt">' +
                '<span>&nbsp;{{project.title}}</span>' +
                    '<div style="float: right;">' +
                        '<span v-on:click="onPause()">' +
                            '<img style="width: 9px" src="img/buttons/pause.svg" alt="">' +
                        '</span>' +
                        '<span v-on:click="onDelete()">' +
                            '<img style="width: 12px" src="img/buttons/close.svg" alt="">' +
                        '</span>' +
                    '</div>' +
            '</div>' +

        '</div>' +
        '<div v-on:click="onEdit()">' +
            '<div class="date-container">' +
                '{{props.titles.dateBegin}} ' +
                '{{new Date(project.begin).toLocaleDateString()}}' +
            '</div>' +
            '<div class="date-container">' +
                '{{props.titles.dateComplete}} ' +
                '{{new Date(project.end).toLocaleDateString()}}' +
            '</div>' +
        '</div>' +
        '<div class="date-container">' +
            '<span>' +
                '{{props.titles.todo}} :' +
                    '<span id="active-tasks" v-if="project.statistic">{{project.statistic.active}}</span>' +
                    '<span id="active-tasks" v-else>0</span>' +
            '</span>' +
        '</div>' +
        '<div class="date-container">' +
            '<span>' +
                '{{props.titles.progressing}} :' +
                    '<span id="canceled-tasks" v-if="project.statistic">{{project.statistic.progressing}}</span>' +
                    '<span id="canceled-tasks" v-else>0</span>' +
            '</span>' +
        '</div>' +
        '<div class="date-container">' +
            '<span>' +
                '{{props.titles.done}} :' +
                    '<span id="done-tasks" v-if="project.statistic">{{project.statistic.done}}</span>' +
                    '<span id="done-tasks" v-else>0</span>' +
            '</span>' +
        '</div>' +
        '<progress-bar v-if="project.statistic" :total="project.statistic.active + project.statistic.progressing + project.statistic.done" :value="project.statistic.done"></progress-bar>' +
        '<progress-bar :total="projectLength()" :value="timeProgress()"></progress-bar>' +
        '<div style="padding: 8px 0"></div>' +
    '</div>'
};
