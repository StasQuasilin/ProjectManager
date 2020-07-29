progressBar = {
    props:{
        size:Number,
        value:Number,
        color:String
    },
    methods:{
        style:function () {
            let style = {};
            if (this.color){
                style.borderColor = this.color;
            }
            return style;
        },
        progressBar:function() {
            let progress = 0;
            if (this.value !== 0) {
                progress = 1.0 * this.value / this.size * 100;
                if (progress > 100) {
                    progress = 100;
                }
            }
            let style = {
                width:progress + '%'
            };
            if (this.color){
                style.backgroundColor = this.color;
            } else {
                style.backgroundColor = 'black';
            }
            return style;
        }
    },
    template:'<div class="progress-bar-body" :style="style()">' +
            '<div class="progress-bar" :style="progressBar()"></div>' +
        '</div>'
};