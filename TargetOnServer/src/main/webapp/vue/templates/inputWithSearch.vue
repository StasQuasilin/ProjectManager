inputSearch = {
    props:{
        object:Object,
        props:Object
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
        putValue:function(item){
            if (this.props.put){
                this.props.put(item);
                this.items = [];
            }
        },
        find:function(){
            if (event.keyCode !== 27 ) {
                clearTimeout(this.timer);
                let input = this.object.title;
                if (input && input.length >= 3) {
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
        }
    },
    template: '<div style="position: relative" v-on:blur="clear">' +
            '<input v-model="object.title"  v-on:keyup.esc.prevent="clear()" v-on:keyup="find()" onfocus="this.select()">' +
            '<div class="custom-data-list" v-if="items.length > 0">' +
                '<div class="custom-data-list-item" v-for="item in items" v-on:click="putValue(item)">' +
                    '{{item}}' +
                '</div>' +
            '</div>' +
        '</div>'
};