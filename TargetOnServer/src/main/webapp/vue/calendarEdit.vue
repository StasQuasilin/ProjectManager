calendarEdit = new Vue({
    el:'#calendarEdit',
    data:{
        api:{},
        repeats:['none','day', 'week', 'month', 'year'],
        calendarItem:{
            id:-1,
            title:'',
            date:new Date(),
            length:1,
            repeat:'none',
            useDate:true,
            useTime:true
        }
    },
    methods:{
        save:function(){
            let item = Object.assign({}, this.calendarItem);
            item.date = item.date.toISOString().replace('T',' ').replace('Z', '');
            PostApi(this.api.save, item, function(a){
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});