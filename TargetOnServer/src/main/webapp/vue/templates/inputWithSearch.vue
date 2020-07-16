inputSearch = {
    props:{
        object:Object,
        props:Object
    },
    data:function (){
        return{
            items:[],
            timer:-1,
            input:''
        }
    },
    mounted:function(){
        this.updateInput();
    },
    methods:{
        updateInput:function(){
            this.input = this.object.title;
        },
        putValue:function(item){
            if (this.props.put){
                this.props.put(item);
            }
            this.clear();
        },
        find:function(){
            if (event.keyCode !== 27 ) {
                clearTimeout(this.timer);
                let input = this.input;
                if (input && input.length >= 3) {
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
            this.items = [];
            console.log('Clear!');
            console.log(this.object);
            const self = this;
            setTimeout(function () {
                self.updateInput();
            }, 0);

        }
    },
    template: '<div style="position: relative" v-on:blur="clear">' +
            '<input v-model="input"  v-on:keyup.esc.prevent="clear()" v-on:keyup="find()" onfocus="this.select()">' +
            '<div class="custom-data-list" v-if="items.length > 0">' +
                '<div class="custom-data-list-item" v-for="item in items" v-on:click="putValue(item)">' +
                    '{{item}}' +
                '</div>' +
            '</div>' +
        '</div>'
};