var calendar = new Vue({
    el:'#calendar',
    components:{
        'calendar-item':calendarBox
    },
    data:{
        api:{},
        days:{},
        date:new Date().toISOString().substring(0, 10),
        items:[],
        calendarItems:[],
        time:0
    },
    mounted:function(){
        this.initTime();
    },
    methods:{
        switchDay:function(val){
            var date = new Date(this.date);
            date.setDate(date.getDate() + val);
            this.date = date.toISOString().substring(0, 10);
            this.getItems();
        },
        getItems:function(){
            const self = this;
            PostApi(this.api.getItems, {date:this.date}, function(a){
                for (var i in a){
                    if (a.hasOwnProperty(i)){
                        var item = a[i];
                        var date = new Date(item.timestamp);
                        item.index = date.getHours();
                    }
                }
                self.calendarItems = a;

                self.checkCalendar();
            })
        },
        initTime:function(){
            this.time = new Date().getHours();
            const self = this;
            let updateTime= function(){
                console.log('init time');
                self.initTime();
            };
            setTimeout(updateTime, (60 - new Date().getMinutes()) * 60 * 1000 )
        },
        handle:function(data){
            for (let i in data.update){
                if (data.update.hasOwnProperty(i)){
                    let item = data.update[i];
                    this.items.push(item);
                }
            }
            this.items = data.items;
            console.log(data);
            this.getCalendar();
        },
        getCalendar:function(){
            let formattedItems = {};
            for (let i in this.items){
                if (this.items.hasOwnProperty(i)){
                    let item = this.items[i];
                    let begin = new Date(item.begin);
                    let end = new Date(item.end);
                    item.length = Math.ceil( (end - begin) / 1000 / 60 / 60 );
                    formattedItems[begin.getHours()] = item;
                }
            }
            let calendar = {};
            for (let i = 0; i < 24; ){
                if(formattedItems[i]){
                    let item = formattedItems[i];
                    calendar[i] = item;
                    i+=item.length;
                } else {
                    calendar[i] = {
                        empty: true
                    };
                    i++
                }
            }
            return calendar;
        },
        add:function(a){
            console.log(a);
            var index = a.newIndex;
            var element = this.calendarItems[index];
            Vue.set(element, 'index', index);
            this.saveTime(element, index, 1);
            this.checkCalendar();
        },
        remove:function(r){
            console.log(r);
            this.checkCalendar();
        },
        checkCalendar:function(){
            var items = this.calendarItems.filter(function(item){
                return item.empty === undefined
            });
            for (var i = 0; i < 24; i++){
                var found = false;
                for (var j in items){
                    if(items.hasOwnProperty(j)){
                        var item = items[j];
                        if(item.index === i){
                            found = true;
                        }
                    }
                }
                if (!found) {
                    items.push({
                        index: i,
                        empty: true,
                        hidden: false
                    })
                }
            }
            items.sort(function(a, b){
                return a.index - b.index;
            });
            this.calendarItems = items;
        },
        checkTime:function(e){
            var index = e.draggedContext.futureIndex;
            var element = e.draggedContext.element;
            element.index = index;
            this.saveTime(element, index, 1);
        },
        saveTime:function(item, index, length){
            PostApi(this.api.editTime, {task:item.id, index:index, length:length}, function(a){
                console.log(a)
            });
        },
        edit:function(id, time){
            console.log('Edit ' + id);
            let param = {
                id:id
            };
            if (time){
                param.date = this.date;
                param.time = time;
            }

            loadModal(this.api.edit, param);
        }
    }
});