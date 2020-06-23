let subscriber = {
    socket : null,
    subscribes:{},
    subscribe:function(subscriber, handler){
        let data = {
            'action':SUBSCRIBE,
            'subscribe':subscriber,
            'user':user
        }
        this.socket.send(JSON.stringify(data));
        this.subscribes[subscriber] = handler;
    },
    send:function(message){
        this.socket.send(message);
    },
    connect:function(){
        this.socket = new WebSocket('ws://' + window.location.host + context + SUBSCRIBE_API);
        this.socket.onopen = function(){
            console.log('OPEN!!')
        }
        this.socket.onmessage = function (env) {
            let json = JSON.parse(env.data);
            console.log(json);
            let subscribe = json.subscribe;
            let data = json.data;
            console.log(subscriber.subscribes);
            let handler = this.subscribes[subscribe];
            if (handler){
                handler(data);
            }
        }
        this.socket.onclose = function () {

        }
    }
}