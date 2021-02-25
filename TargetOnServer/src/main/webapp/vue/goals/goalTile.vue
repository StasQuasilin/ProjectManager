goalTile = {
    props:{
        goal:Object,
        props:Object,
        tree:Function
    },
    components:{
        'progress-bar':progressBar
    },
    methods:{
        onClick:function () {
            if(typeof this.tree === "function"){
                this.tree(this.goal.titleId);
            } else {
                console.warn('---');
            }
        }
    },
    template:'<div>' +
            '<div class="goal-header">' +
                '<span class="goal-title" v-on:click="onClick">' +
                    '{{goal.title}}' +
                '</span>' +
                '<div class="goal-title-buttons">' +
                    '<span class="text-button" v-on:click="props.edit(goal.id)">' +
                        '&#10000;' +
                    '</span>' +
                    '<span class="text-button" v-on:click="props.remove(goal.id)">' +
                        '&times;' +
                    '</span> ' +
                '</div>' +
            '</div>' +
            '<div class="goal-content" v-on:click="onClick">' +
                '<div v-if="goal.begin">' +
                    '{{props.dateBegin}}: ' +
                    '<span>' +
                        '{{new Date(goal.begin).toLocaleDateString()}}' +
                    '</span>' +
                '</div>' +
                '<div v-if="goal.end">' +
                    '{{props.dateEnd}}: ' +
                    '<span>' +
                        '{{new Date(goal.end).toLocaleDateString()}}' +
                    '</span>' +
                '</div>' +
                '<div >' +
                    '<progress-bar v-if="goal.begin && goal.end" :size="(new Date(goal.end) - new Date(goal.begin))" ' +
                        ':value="(new Date() - new Date(goal.begin))" :color="\'green\'"></progress-bar>' +
                    '<progress-bar v-if="goal.statistic" ' +
                        ':size="goal.statistic.active + goal.statistic.progressing + goal.statistic.done" ' +
                        ':value="goal.statistic.done" :color="\'orange\'"></progress-bar>' +
                '</div>' +
                '<div v-if="goal.statistic && (goal.budget || goal.statistic.minus || goal.statistic.plus)">' +
                    '{{props.budget}}: ' +
                    '<span v-if="goal.statistic.minus || goal.statistic.plus">' +
                        '{{Math.abs(goal.statistic.minus + goal.statistic.plus).toLocaleString()}}' +
                    '</span>' +
                    '<span>' +
                        ' / ' +
                    '</span>' +
                    '<span>' +
                        '{{goal.budget.toLocaleString()}}' +
                    '</span>' +
                    '<progress-bar v-if="goal.budget && goal.statistic" :size="goal.budget" ' +
                        ':value="goal.statistic.minus + goal.statistic.plus"></progress-bar>' +
                '</div>' +
                '<div v-if="goal.statistic">' +
                    '<div>' +
                        '{{props.activeTasks}}: ' +
                        '{{goal.statistic.active}}' +
                    '</div>' +
                    '<div>' +
                        '{{props.progressingTasks}}: ' +
                        '{{goal.statistic.progressing}}' +
                    '</div>' +
                    '<div>' +
                        '{{props.doneTasks}}: ' +
                        '{{goal.statistic.done}}' +
                    '</div>' +

                '</div>' +
                '<div style="font-size: 8pt; color: gray">' +
                    '{{goal.statistic}}' +
                '</div>' +

            '</div>' +
        '</div>'
};