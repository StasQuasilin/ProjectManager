closeEvents = [];
saveEvents = [];
function addOnCloseEvent(event){
    closeEvents.push(event);
}
function closeModal(){
    for (let e in closeEvents){
        if (closeEvents.hasOwnProperty(e)){
            closeEvents[e]();
        }
    }
}
function addOnSaveEvent(event){
    saveEvents.push(event);
}
function saveModal(result){
    for (let a in saveEvents){
        if (saveEvents.hasOwnProperty(a)){
            let save = saveEvents[a];
            if (typeof save === 'function') {
                save(result)
            }
        }
    }
    saveEvents.splice(0, saveEvents.length);
}