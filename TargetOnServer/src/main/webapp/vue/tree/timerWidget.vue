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
           console.log(data);
           if(data.update){
               data.add = data.update;
           }
           if (data.add) {
               this.timer = data.add;
               this.updateLength();
           } else if (data.remove){
               this.timer.begin = null;
           }
           this.$forceUpdate();
       }
    }
});