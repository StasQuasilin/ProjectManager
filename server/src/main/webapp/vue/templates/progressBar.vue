var bar = {
    props:{
        total:Number,
        value:Number
    },
    methods:{
        getPercentage:function(){
            return ((this.value / this.total) * 100) +'%';
        }
    },
    template:'<div class="progress-bar-background">' +
            '<div :style="{width: getPercentage()}" class="progress-bar">&nbsp;</div>' +
        '</div>'
};