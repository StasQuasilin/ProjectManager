goalTile = {
    props:{
        goal:Object,
        props:Object
    },
    components:{
        'progress-bar':progressBar
    },
    template:'<div>' +
            '<div class="goal-title">' +
                '{{goal.id}}:{{goal.title}}' +
                '<div class="goal-title-buttons">' +
                    '<span class="text-button" v-on:click="props.tree(goal.category)">' +
                        '&#127795;' +
                    '</span>' +
                    '<span class="text-button" v-on:click="props.edit(goal.id)">' +
                        '&#10000;' +
                    '</span>' +
                    '<span class="text-button" v-on:click="props.remove(goal.id)">' +
                        '&times;' +
                    '</span> ' +
                '</div>' +
            '</div>' +
            '<div class="goal-content">' +
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
                '<div v-if="goal.budget">' +
                    '{{props.budget}}: ' +
                    '<span>' +
                        '{{goal.budget.toLocaleString()}} {{goal.currency}}' +
                    '</span>' +
                    '<progress-bar :size="10" :value="4"></progress-bar>' +
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