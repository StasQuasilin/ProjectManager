var bar = {
    props:{
        total:Number,
        value:Number,
        color:String
    },
    methods:{
        getPercentage:function(){
            return ((this.value / this.total) * 100) +'%';
        },
        getStyle:function(){
            if (this.color){
                return {
                    'border-color':this.color,
                    'progress-bar':{
                        'background-color':this.color
                    }
                }
            } else {
                return {}
            }
        }
    },
    template:'<div class="progress-bar-background"' +
        ':style="{\'border-color\' : color}">' +
            '<div :style="{width : getPercentage() , \'background-color\' : color}" ' +
            'class="progress-bar">&nbsp;</div>' +
        '</div>'
};