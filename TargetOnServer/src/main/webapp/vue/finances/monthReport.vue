report = new Vue({
    el:'#reportView',
    data:{
        api:{},
        date:new Date(),
        result:{}
    },
    mounted:function () {
        this.date.setDate(1);
        this.$forceUpdate();
    },
    methods:{
        changeMonth:function (val) {
            this.date.setMonth(this.date.getMonth() + val);
            this.$forceUpdate();
            this.buildReport();
        },
        buildReport:function () {
            const self = this;
            PostApi(this.api.build, {date:this.date.toISOString().substring(0, 10)}, function (a) {
                if (a.status === 'success'){
                    self.result = a.result;
                }
            })
        }
    }
});