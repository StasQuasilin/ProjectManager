goalTile = {
    props:{
        goal:Object,
        props:Object,
        tree:Function
    },
    mixins:[timeUtil],
    components:{
        'progress-bar':progressBar
    },
    mounted:function(){
        if(this.goal.statistic && this.goal.statistic.spend > 0) {
            this.time = this.goal.statistic.spend;
        }
    },
    methods:{
        onClick:function () {
            if(typeof this.tree === "function"){
                this.tree(this.goal.titleId);
            } else {
                console.warn('---');
            }
        },
        openMemberList:function () {
            console.log(this.props.memberList);
            if(this.props.memberList){
                loadModal(this.props.memberList, {id:this.goal.id})
            }
        }
    },
    template:'<div style="display: flex">' +
        '<div v-if="goal.active" class="active-goal"></div>' +
        '<div style="width: 100%">' +
            '<div class="goal-header">' +
                '<span class="goal-title" v-on:click="onClick">' +
                    '{{goal.title}} by {{goal.owner.surname}} {{goal.owner.forename}}' +
                '</span>' +
                '<div class="goal-title-buttons">' +
                    '<span class="text-button" title="Goal members" v-on:click="openMemberList()">' +
                        '<img style="background: transparent; width: 10pt" :src="props.memberIcon" alt="M"> {{(1+goal.members.length).toLocaleString()}}' +
                    '</span>' +
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
                        ':size="goal.statistic.active + goal.statistic.progressing + goal.statistic.done + goal.statistic.other" ' +
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
                '<div v-if="goal.statistic && (goal.statistic.active + goal.statistic.progressing + goal.statistic.done + goal.statistic.other) > 0" style="font-size: 10pt">' +
                    '<div v-if="goal.statistic.active > 0">' +
                        '{{props.activeTasks}}: ' +
                        '{{goal.statistic.active.toLocaleString()}}' +
                    '</div>' +
                    '<div v-if="goal.statistic.progressing > 0">' +
                        '{{props.progressingTasks}}: ' +
                        '{{goal.statistic.progressing.toLocaleString()}}' +
                    '</div>' +
                    '<div v-if="goal.statistic.done > 0">' +
                        '{{props.doneTasks}}: ' +
                        '{{goal.statistic.done.toLocaleString()}}' +
                    '</div>' +
                    '<div v-if="goal.statistic.other > 0">' +
                        '{{props.otherTasks}}: ' +
                        '{{goal.statistic.other.toLocaleString()}}' +
                    '</div>' +
                '</div>' +
                '<div v-if="goal.statistic.spend > 0">' +
                    '{{props.spendTime}}:' +
                        '<template v-if="timeLength.hours > 0">' +
                            ' {{prettyNumber(timeLength.hours)}} hr' +
                        '</template>' +
                        '<template v-if="timeLength.min > 0">' +
                            ' {{prettyNumber(timeLength.min)}} min' +
                        '</template>' +
                        '<template v-if="timeLength.sec > 0">' +
                            ' {{prettyNumber(timeLength.sec)}} sec' +
                        '</template>' +
                '</div>' +
                '<div style="font-size: 8pt; color: gray">' +
                    '{{goal.statistic}}' +
                '</div>' +

            '</div>' +
        '</div>' +
    '</div>'
};