inputSearch = {
    props:{
        object:Object,
        props:Object,
        width:String,
        enable:Boolean,
        show:Array,
        create:true,
        additionally:Object
    },
    data:function (){
        return{
            items:[],
            timer:-1,
            addTimer:-1,
            oldObject:{},
            canAdd:false
        }
    },
    mounted:function(){
        if (!this.object){
            console.warn('Object is ' + typeof this.object);
        }
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
            this.clear();
        },
        putNew:function(){
            if(this.items.length === 1){
                this.putValue(this.items[0]);
            } else if (this.items.length === 0 && this.create) {
                this.putValue(Object.assign({}, this.object));
            }
        },
        buildData:function(){

        },
        onKeyUp:function(){
            if (event.keyCode !== 27 ) {
                this.find();
            }
        },
        find:function(){
            this.items = [];
            this.canAdd = false;
            clearTimeout(this.timer);
            let input = this.object.title;

            if (input && input.length >= 1) {
                this.object.id = -1;
                const self = this;
                this.timer = setTimeout(function () {
                    let data = Object.assign({},self.additionally !== null ? self.additionally : {});
                    data.key = input;
                    PostApi(self.props.findCategory, data, function (a) {
                        // console.log(a);
                        if (a.status === 'success'){
                            self.items = a.result;
                            if (self.create) {
                                self.addTimer = setTimeout(function () {
                                    self.canAdd = true;
                                }, 3000);
                            }
                        }
                    })
                }, 500);
            }
        },
        clear:function () {
            this.object.id=-1;
            this.object.title='';

            this.items = [];
            this.canAdd = false;
            clearTimeout(this.timer);
            clearTimeout(this.addTimer);
        },
        inputStyle:function () {
            let style = {};
            if (this.width){
                style.width = this.width;
            }
            return style;
        },
        hasPrefix:function () {
            return typeof this.prefix !== "undefined";
        }
    },
    template: '<div v-if="object" style="position: relative; display: flex" :style="inputStyle()" v-on:blur="clear">' +
            '<span v-if="hasPrefix()">' +
                '{{prefix()}}' +
            '</span>' +
            '<input v-model="object.title" :disabled="isEnabled()" ' +
                'v-on:keyup.esc.prevent="clear()" v-on:keyup.enter.prevent="putNew()" ' +
                    'v-on:keyup="onKeyUp()" onfocus="this.select()">' +
            '<div class="custom-data-list" v-if="items.length > 0 || canAdd">' +
                '<div class="custom-data-list-item" v-for="item in items" v-on:click="putValue(item)">' +
                    '<template v-if="show !== null">' +
                        '<span v-for="s in show">' +
                            '{{item[s]}}' +
                        '</span>' +
                    '</template>' +
                    '<span v-else>' +
                        '{{item}}' +
                    '</span>' +
                '</div>' +
                '<div v-if="(items.length == 0 || canAdd) && create" v-on:click="putNew()">' +
                    '<span v-if="show != null">' +
                        '+ {{object[show]}}' +
                    '</span>' +
                '</div>' +
            '</div>' +
        '</div>'
};