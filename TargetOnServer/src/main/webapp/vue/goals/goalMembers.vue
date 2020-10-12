goalMembers = new Vue({
    el:'#memberList',
    data:{
        api:{},
        item:-1,
        owner:{},
        friends:[],
        members:[]
    },
    methods:{
        save:function () {
            let members = [];
            for  (let i = 0; i < this.friends.length ;i++){
                let friend = this.friends[i];
                if (friend.member){
                    members.push(friend.id);
                }
            }
            PostApi(this.api.save, {item: this.item, members:members}, function (a) {
                if(a.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});