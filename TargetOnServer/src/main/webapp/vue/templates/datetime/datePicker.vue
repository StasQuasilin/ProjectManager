datePicker = {
    props:{
        date:String,
        props:Object
    },
    mounted:function(){
        this._date = this.date;
    },
    data:function(){
        return {
            _date:'',
            locale:'uk',
            show:false
        }
    },
    methods:{
        put:function () {
            this.show = false;
            if (this.props.put){
                this.props.put(this._date);
            }
        }
    },
    template:'<div style="position: relative; display: inline-block">' +
            '<div class="text-button" v-on:click="show=!show">' +
                '{{new Date(date).toLocaleDateString()}}' +
            '</div>' +
            '<div v-if="show" style="position: absolute; z-index: 2">' +
                '<v-date-picker ' +
                    'class="date-picker" ' +
                    ':no-title="true" ' +
                    ':locale="locale" ' +
                    ':first-day-of-week="1" ' +
                    '@input="put"' +
                    'v-model="_date">' +
                '      </v-date-picker>' +
            '</div>' +

        '</div>'
};