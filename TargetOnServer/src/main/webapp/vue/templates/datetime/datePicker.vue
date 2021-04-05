datePicker = {
    props:{
        date:String,
        props:Object
    },
    mounted:function(){
        if (this.date){
            this._date = this.date;
        } else {
            this._date = new Date().toISOString().substring(0, 10);
        }

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
            } else {
                console.warn('Props not contain methods \'put\'');
            }
        }
    },
    template:'<div style="display: inline-block; background-color: transparent">' +
            '<div class="text-button" style="background-color: transparent" v-on:click="show=!show">' +
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
                    '</v-date-picker>' +
            '</div>' +

        '</div>'
};