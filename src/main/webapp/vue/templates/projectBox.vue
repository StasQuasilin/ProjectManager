var projectBox = {
    props:{
        project:Object,
        props:Object
    },
    methods:{
        onDelete:function(){
            if (this.props.onDelete){
                this.props.onDelete(this.project.id);
            }
        },
        onEdit:function(){
            console.log('Edit');
        }
    },
    template:'<div style="border: solid black 1pt">' +
        '<div class="project-box-title">' +
            '<span>&nbsp;{{project.title}}</span>' +
            '<span v-on:click="onDelete()">&times;</span>' +
        '</div>' +
        '<div v-on:click="onEdit()">' +
            '<div class="date-container">' +
                '{{new Date(project.begin).toLocaleDateString()}}' +
            '</div>' +
            '<div class="date-container">' +
                '{{new Date(project.end).toLocaleDateString()}}' +
            '</div>' +
        '</div>' +
    '</div>'
};
//<div class="date-container">
//    <span>
//    <fmt:message key="project.edit.date-begin"/>: {{new Date(value.begin).toLocaleDateString()}}
//</span>
//</div>
//<div class="date-container">
//    <span>
//    <fmt:message key="project.edit.date-complete"/>: {{new Date(value.end).toLocaleDateString()}}
//</span>
//</div>
//<div class="budget-container">
//    <span>
//    <fmt:message key="project.budget"/>:
//<span v-if="value.budget">0/{{value.budget.amount}}</span>
//<span v-else>
//0
//</span>
//</span>
//</div>
//<div class="date-container">
//    <span style="font-weight: bold">
//    <fmt:message key="project.tasks"/>
//    </span>
//    </div>
//    <div class="date-container">
//    <span>
//    <fmt:message key="project.tasks.active"/>:
//<span id="active-tasks">{{value.active}}</span>
//</span>
//</div>
//<div class="date-container">
//    <span>
//    <fmt:message key="project.tasks.canceled"/>:
//<span id="canceled-tasks">{{value.canceled}}</span>
//</span>
//</div>
//<div class="date-container">
//    <span>
//    <fmt:message key="project.tasks.done"/>:
//<span id="done-tasks">{{value.done}}</span>
//</span>
//</div>
//<div class="progress-bar-background">
//    <div class="progress-bar" :style="{width:value.time + '%'}"></div>
//    </div>
//    <div class="progress-bar-background">
//    <div class="progress-bar"></div>
//    </div>