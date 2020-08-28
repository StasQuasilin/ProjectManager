kanban = new Vue({
    el:'#kanban',
    mixins:[list],
    data:function () {
        return {
            statuses:[],
            statusNames:{},
            goals:[],
            goal:-1
        }
    },
    methods:{
        getItemsByStatus:function(status){
            return this.getItems().filter(function (item) {
                return item.status === status;
            })
        }
    }
});