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
        time:0,
        scale:1
    },
    mounted:function(){
        for (let i = 0; i < 24; i++){
            this.calendarItems.push({
                index:i,
                empty: true,
                item:null
            });
        }
        // this.initTime();
    },
    computed:{
    },
    methods:{
        Scale:function(event){
            console.log(event);
        },
        contextMenu:function(e){
            e.preventDefault();
        },
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
                        let item = a[i];
                        let date = new Date(item.timestamp);
                        item.index = date.getHours();
                    }
                }
                // self.calendarItems = a;
                // self.checkCalendar();
            })
        },
        getCalendar:function(){
            let formattedItems = {};
            for (let i in this.calendarItems){
                if (this.calendarItems.hasOwnProperty(i)){
                    let item = this.calendarItems[i];
                    let begin = item.begin;
                    let end = item.end;
                    item.length = Math.ceil( (end - begin) / 1000 / 60 / 60 );
                    formattedItems[begin.getHours()] = item;
                }
            }
            let calendar = {};
            for (let i = 0; i < 24; ){
                if(formattedItems[i]){
                    let item = formattedItems[i];
                    calendar[i] = [item];
                    i+=item.length;
                } else {
                    calendar[i] = [];
                    i++
                }
            }
            return calendar;
        },
        initTime:function(){
            this.time = new Date().getHours();
            const self = this;
            let updateTime = function(){
                console.log('init time');
                self.initTime();
            };
            setTimeout(updateTime, (59 - new Date().getMinutes()) * 60 * 1000 )
        },
        handle:function(data){
            for (let i in data.update){
                if (data.update.hasOwnProperty(i)){
                    let item = data.update[i];
                    this.items.push(item);
                }
            }
            for (let i in data.items){
                if (data.items.hasOwnProperty(i)){
                    let item = data.items[i];
                    item.begin = new Date(item.begin);
                    item.end = new Date(item.end);
                    let hours = item.begin.getHours();
                    this.calendarItems.splice(hours, 1, item);
                }
            }
            // this.calendarItems = data.items;
        },
        dragStart:function(item){
            if (item.empty || item.clicked){
                window.event.preventDefault();
            }
            console.log(item);
        },
        drag:function(e){
            console.log('Drag end');
            console.log(e);
        },
        add:function(a){
            console.log('add');
            console.log(a);
            // let item = this.calendarBuffer.splice(0, 1)[0];

            let index = a.newIndex;

            // this.calendarItems.splice(index + 1, 1);
            this.setIndices();
            let element = this.calendarItems[index];
            element.length = 1;
            // console.log(item);
            // this.calendarItems.push({
            //     begin:this.date + ' ' + index + ':00:00',
            //     end:this.date + ' ' + (index + 1) + ':00:00',
            //     task:item
            // })
            // console.log(element);
            // Vue.set(element, 'index', index);
            // this.saveTime(element, index, 1);
            // this.checkCalendar();
        },
        drop:function(d){

            console.log("Drop");

            let oldIndex = d.oldIndex;
            let newIndex = d.newIndex;
            console.log('New index = ' + newIndex);
            // this.setIndices();
            // Vue.set(this.calendarItems[newIndex], 'index', newIndex);
            Vue.delete(this.calendarItems[newIndex], 'move');

            // let item = this.calendar[oldIndex];
            // let hours = newIndex - oldIndex;
            // item.begin.setHours(item.begin.getHours() + hours);
            // item.end.setHours(item.end.getHours() + hours);
            // this.getCalendar();
        },
        setIndices:function(next){
            for (let i in this.calendarItems){
                if (this.calendarItems.hasOwnProperty(i) &&  i !== next){
                    Vue.set(this.calendarItems[i], 'index', i);
                }
            }
        },
        move:function(m){
            // console.log(m);
            let index= m.draggedContext.index;
            // let item = this.calendar[index];
            let next = m.relatedContext.index;
            // let hours = next - index;
            // console.log('From: ' + index + ' to ' + next);


            this.setIndices(next);
            Vue.set(this.calendarItems[index], 'index', next);
            Vue.set(this.calendarItems[index], 'move', true);
            // Vue.set(item, 'index', next);
            // console.log(this.calendarItems[index].index);

        },
        remove:function(r){
            console.log(r);
            // this.checkCalendar();
        },
        checkCalendar:function(){
            let items = this.calendarItems.filter(function(item){
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
            console.log(e);
            // var index = e.draggedContext.futureIndex;
            // var element = e.draggedContext.element;
            // element.index = index;
            // this.saveTime(element, index, 1);
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