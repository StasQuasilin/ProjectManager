timerWidget = new Vue({
   el:'#timerWidget',
    mixins:[timer],
    data:function(){
       return {
           api:{}
       }
    },
    methods:{
       handler:function(data){
           if (data.add){
               this.timer = data.add;
               this.updateLength();
           } else if (data.remove){
               this.timer.begin = null;
           }
       }
    }
});