goalTile = {
    props:{
        goal:Object,
        props:Object
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
                    '</span> ' +
                '</div>' +
            '</div>' +
            '<div v-if="goal.begin">' +
                'Begin date:' +
                '<span>' +
                    '{{new Date(goal.begin).toLocaleDateString()}}' +
                '</span>' +
            '</div>' +
            '<div v-if="goal.budget">' +
                'Budget:' +
                '<span>' +
                    '{{goal.budget.toLocaleString()}} {{goal.currency}}' +
                '</span>' +
            '</div>' +
            '<div>' +
                '{{goal.statistic}}' +
            '</div>' +
            '' +
        '</div>'
};