friendsList = new Vue({
    el:'#friends',
    mixins:[
        list
    ],
    data:function () {
        return{
            api:{},
            searchText:'',
            timer:-1
        }
    },
    methods:{
        searchUser:function(){
            if (event.key !== 'Escape'){
                clearTimeout(this.timer);
                if (this.searchText.length > 3) {
                    const self = this;
                    this.timer = setTimeout(function () {
                        PostApi(self.api.findUser, {key:self.searchText}, function (a) {
                            console.log(a);
                        })
                    }, 200);

                }
            } else {
                this.searchText = '';
            }
        }
    }
});