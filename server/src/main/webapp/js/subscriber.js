const subscriber = {
    socket:{},
    subscribers:{},
    unSubscribers:[],
    waitTimeout:800,
    init:function(protocol, server, address){
        console.log(protocol + server + address);
        this.socket = new WebSocket(protocol + server + address);
        this.socket.onclose = function(cause){
            console.log(cause);
            lock();
            const self = this;
            setTimeout(function () {
                console.log(self.subscribers);
            }, 3000)
        };
        const self = this;
        this.socket.onmessage = function(env){
            let json = JSON.parse(env.data);
            console.log(json);
            let type = json['type'];
            let data = json['data'];
            if (type && self.subscribers[type]) {
                let func = self.subscribers[type];
                if (typeof func === 'function') {
                    func(data, type);
                }
            }
        };
    },
    subscribe:function(subscriber, handler){
        this.subscribers[subscriber] = handler;
        this.send(JSON.stringify({
            action:'subscribe',
            subscriber:subscriber,
            user:UserID
        }))
    },
    unsubscribeAll:function(){
        for (let i in this.unSubscribers){
            if (this.unSubscribers.hasOwnProperty(i)){
                let unsub = this.unSubscribers[i];
                this.unsubscribe(unsub);
            }
        }
        this.unSubscribers = [];
    },
    unsubscribe:function(subscriber){
        console.log(subscriber);
        delete this.subscribers[subscriber];
        this.send(JSON.stringify({
            action:'unsubscribe',
            subscriber:subscriber
        }))
    },
    send:function(msg){
        console.log(msg);
        if(this.socket.readyState === WebSocket.OPEN){
            this.socket.send(msg);
        } else if (this.socket.readyState === WebSocket.CONNECTING){
            const self = this;
            let waitFunc = function(){
                self.send(msg)
            };
            setTimeout(waitFunc, this.waitTimeout)
        }
    },
    close:function(){
        this.socket.close();
    }
};