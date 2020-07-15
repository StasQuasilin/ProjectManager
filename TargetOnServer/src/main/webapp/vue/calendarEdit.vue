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
            let d = item.date;
            item.date =  d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' ' + d.getHours() + ':' + d.getMinutes() + ':00';
            PostApi(this.api.save, item, function(a){
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});