timePicker = {
    props:{
        time:String,
        props:Object
    },
    mounted:function(){
        this._time = this.time;
    },
    data:function(){
        return {
            _time:'',
            show:false
        }
    },
    methods:{
        put:function () {
            this.show=false;
            if (this.props.put){
                this.props.put(this._time);
            }
        }
    },
    template:'<div style="position: relative; display: inline-block; top: 0">' +
            '<div v-if="time" class="text-button" v-on:click="show=!show">' +
                '{{time.substring(0, 5)}}' +
            '</div>' +
            '<div v-if="show" style="position: absolute;">' +
                '<v-time-picker style="display: inline-block" ' +
                    ':width="150" v-on:change="put" ' +
                    'v-model="_time" format="24hr">' +
                '</v-time-picker>' +
            '</div>' +
        '</div>'
};