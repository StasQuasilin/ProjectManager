function GetChildElemById(parent, childId){
    let elements = parent.getElementsByTagName("*");
    for (let i = 0; i < elements.length; i++){
        if (elements[i].id === childId){
            return elements[i];
        }
    }
}
function randomUUID(){
    const s4 = function(){
        return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
    };
    return s4() + '-' + s4() + '-' + s4() + s4() + '-' + s4() + '-' + s4();
}