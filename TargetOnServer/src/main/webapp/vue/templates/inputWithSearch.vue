inputSearch = {
    props:{
        object:Object,
        props:Object,
        width:String,
        enable:Boolean
    },
    data:function (){
        return{
            items:[],
            timer:-1,
            oldObject:{}
        }
    },
    mounted:function(){
        this.oldObject = this.object;
    },
    methods:{
        isEnabled:function(){
            return this.enable == null || this.enable;
        },
        putValue:function(item){
            if (this.props.put){
                this.props.put(item);
                this.items = [];
            }
        },
        find:function(){
            if (event.keyCode !== 27 ) {
                this.items = [];
                clearTimeout(this.timer);
                let input = this.object.title;
                if (input && input.length >= 1) {
                    this.object.id = -1;
                    const self = this;
                    this.timer = setTimeout(function () {
                        PostApi(self.props.findCategory, {key: input}, function (a) {
                            self.items = a;
                        })
                    }, 500);
                }
            }
        },
        clear:function () {
            this.object = this.oldObject;
            this.items = [];
        },
        inputStyle:function () {
            let style = {};
            if (this.width){
                style.width = this.width;
            }
            return style;
        }
    },
    template: '<div style="position: relative" :style="inputStyle()" v-on:blur="clear">' +
            '<input v-model="object.title" :disabled="isEnabled()" ' +
                'v-on:keyup.esc.prevent="clear()" v-on:keyup="find()" onfocus="this.select()">' +
            '<div class="custom-data-list" v-if="items.length > 0">' +
                '<div class="custom-data-list-item" v-for="item in items" v-on:click="putValue(item)">' +
                    '{{item.title}}' +
                '</div>' +
            '</div>' +
        '</div>'
};