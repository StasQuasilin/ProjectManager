calendar = new Vue({
    el:'#calendar',
    mixins:[list, pathBuilder, weekNumber],
    data:function(){
        return{
            date:new Date().toISOString().substring(0, 10),
            scales:['day', 'week', 'month'],
            scale:'day',
            calendar:{}
        }
    },
    methods:{
        switchDate:function(value){
            let date = new Date(this.date);
            date.setDate(date.getDate() + value);
            this.date = date.toISOString().substring(0, 10);
        },
        getItems:function () {
            const self = this;
            PostApi(this.api.getCalendar, {date:this.date}, function (a) {
                self.calendar = a;
            })
        }
    }
});