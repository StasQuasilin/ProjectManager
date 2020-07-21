calendar = new Vue({
    el:'#calendar',
    mixins:[list, pathBuilder, weekNumber],
    data:function(){
        return{
            date:new Date().toISOString().substring(0, 10),
            scales:['day', 'week', 'month'],
            scale:'day',
            calendar:[]
        }
    },
    methods:{
        calendarHandler:function(data){
            if (data.update){
                let update = data.update;
                let date = update.date;
                if (!date || date === this.date){
                    this.calendar.push(update);
                }
            }
        },
        switchDate:function(value){
            let date = new Date(this.date);
            date.setDate(date.getDate() + value);
            this.setDate(date);
        },
        resetDate:function(){
            this.setDate(new Date());
        },
        setDate:function(date){
            this.date = date.toISOString().substring(0, 10);
            this.getCalendar();
        },
        getCalendar:function () {
            const self = this;
            PostApi(this.api.getCalendar, {date:this.date}, function (a) {
                self.calendar = a;
            })
        },
        getCalendarItems:function(){
            let items = this.calendar.filter(function (item) {
                return item.time;
            });
            items.sort(function (a, b) {
                return a.time - b.time;
            });
            return items;
        },
        getOtherItems:function(){
            let items = this.calendar.filter(function(item){
                return !item.date || !item.time;
            });
            items.sort(function (a, b) {
                if (!a.date && !b.date){
                    return a.title.localeCompare(b.title);
                } else if (!a.date){
                    return 1;
                } else if (!b.date){
                    return -1;
                } else {
                    return a.date - b.date;
                }
            });
            return items;
        }
    }
});