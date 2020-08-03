calendar = new Vue({
    el:'#calendar',
    mixins:[list, pathBuilder, weekNumber],
    data:function(){
        return{
            date:new Date().toISOString().substring(0, 10),
            scales:['day', 'week', 'month'],
            scale:'day',
            calendar:[],
            events:[]
        }
    },
    computed:{
        calendarStructure:function(){
            let structure = {};
            for (let i = 0; i < 3 * 60; i++){
                let events = this.getEventsByTime(i);
                if (events){
                    structure[i] = events;
                }
            }
            return structure;
        }
    },
    methods:{
        getEventsByTime:function(time){
            let events = this.events.filter(function (item) {
                return item.time === time;
            });
            if (events.length > 0) {
                events.sort(function (a, b) {
                    return b.duration - a.duration;
                });
                let first = events[0];
                let length = this.getEventsBetween(first.time, first.time + first.duration).length;
                let offset = this.getEvents(first.time).length;
                return {
                    events,
                    length,
                    offset
                }
            }
        },
        getEvents:function(time){
            return this.events.filter(function (item) {
                return item.time <= time && item.time + item.duration >= time;
            })
        },
        getEventsBetween:function(from, to){
            return this.events.filter(function (item) {
                return item.time >= from && item.time + item.duration <= to;
            })
        },
        getStyle:function(event, index, events){
            let width = 90 / events.length;
            return {
                width: width + '%',
                left: (index * width) + '%',
                height:event.length * 1.5 + 'pt'
            }
        },
        calendarBuilder:function(){
            return this.calendarStructure;
        },
        buildCalendar:function(){
            let list = this.calendar.filter(function (item) {
                return item.time;
            });
            list.sort(function (a, b) {
                return new Date(a.date + ' ' + a.time) - new Date(b.date + ' ' + b.time) ;
            });

            let date = new Date(this.date);
            date.setHours(0);
            date.setMinutes(0);
            date.setSeconds(0);
            for (let i in list){
                if (list.hasOwnProperty(i)){
                    let item = list[i];
                    let from = new Date(item.date + ' ' + item.time);
                    let to = new Date(from);
                    let l = item.length;
                    to.setMinutes(to.getMinutes() + l);
                    item.from = from;
                    item.to = to;
                }
            }
            let calendar = {};

            for (let i = 0; i < list.length; i++){
                let item1 = list[i];
                calendar[item1.from.toLocaleTimeString()] = item1;
                if (i < list.length - 1){
                    let item2 = list[i + 1];
                    if (item1.to !== item2.from){
                        calendar[item1.to.toLocaleTimeString()] = {
                            from:item1.to,
                            to:item2.from,
                            length:(item2.from - item1.to) / 1000 / 60
                        }
                    }
                }
            }

            return calendar;
        },
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
            this.getCalendarData();
        },
        getCalendarData:function () {
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