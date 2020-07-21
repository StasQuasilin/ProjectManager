calendar = new Vue({
    el:'#calendar',
    mixins:[list, pathBuilder, weekNumber],
    data:function(){
        return{
            date:new Date().toISOString().substring(0, 10),
            scales:['day', 'week', 'month'],
            scale:'day',
            calendar:[
                {
                    date:'2020-07-21',
                    time:'08:30:00',
                    title:'Item 2',
                    length: 3 * 60
                },
                {
                    date:'2020-07-20',
                    time:'23:00:00',
                    title:'Item 1',
                    length: 8 * 60
                },
                {
                    date: '2020-07-21',
                    time:'11:30:00',
                    title: 'Item 3',
                    length: 20
                },
                {
                    date: '2020-07-21',
                    time: '22:50',
                    title: 'Item 4',
                    length: 8 * 60
                }
            ]
        }
    },
    methods:{
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
            let map = {};
            for (let i in list){
                if (list.hasOwnProperty(i)){
                    let item = list[i];
                    let from = new Date(item.date + ' ' + item.time);
                    let to = new Date(from);
                    let l = item.length;
                    to.setMinutes(to.getMinutes() + l);
                    item.from = from.toLocaleString();
                    item.to = to.toLocaleString();


                    let difference = (from - date) / 1000 / 60;
                    if (difference < 0){
                        item.difference = difference;
                    }
                    let min = from < date ? date.toLocaleTimeString() : from.toLocaleTimeString();
                    map[min] = item;
                }
            }

            let calendar = [];
            for (let i = 0; i < 24; i++){
                let hour = date.toLocaleTimeString();
                console.log('Hour: ' + date.toLocaleString());
                let add = true;
                let minutes = date.getMinutes();

                for (let j = minutes; j < 60; j++){
                    let time = date.toLocaleTimeString();
                    if (map[time]){
                        add = false;
                        if (j > 0) {
                            calendar.push({
                                time: hour,
                                length: j
                            });
                        }
                        let item = map[time];
                        item.key = time;
                        let length = item.length;
                        if (item.difference){
                            length += item.difference;
                        }
                        calendar.push(item);
                        console.log(date.toLocaleString());
                        date.setMinutes(date.getMinutes() + length);
                        console.log(date.toLocaleString())
                    } else {
                        date.setMinutes(date.getMinutes() + 1);
                    }
                }

                if (add){
                    console.log('add hour: ' + hour);
                    calendar.push({
                        time:hour,
                        length:60 - minutes
                    })
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
                let date = new Date(self.date);
                date.setDate(date.getDate() - 1);
                date.setHours(22);
                date.setMinutes(50);
                self.calendar.push({
                    time:date.toString(),
                    title:'Test'
                })
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