calendarEdit = new Vue({
    el:'#calendarEdit',
    components:{
        'input-search':inputSearch,
        'date-picker':datePicker,
        'time-picker':timePicker,
    },
    data:{
        api:{},
        repeats:['none','day', 'week', 'month', 'year'],
        useDate:true,
        useTime:true,
        calendarItem:{
            id:-1,
            category:{
                id:-1,
                title:''
            },
            date:null,
            time:null,
            length:'01:00',
            repeat:'none',
        },
        dateProps:{
            put:function (date) {
                calendarEdit.calendarItem.date = date;
            }
        },
        timeProps:{
            put:function (time) {
                calendarEdit.calendarItem.time = time;
            }
        },
        props:{
            put:function(category){
                calendarEdit.calendarItem.category = category;
            }
        }
    },
    methods:{
        save:function(){
            let item = Object.assign({}, this.calendarItem);
            if (!this.useDate){
                delete item.date;
            }
            if (!this.useTime){
                delete item.time;
            } else if (item.time.length === 5){
                item.time += ':00';
            }
            if (item.length === 5){
                item.length += ':00';
            }
            PostApi(this.api.save, item, function(a){
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});