<script src="${context}/vue/tree/treeItem.vue"></script>
<script>
    Vue.component('tree-item', treeItem);
</script>
<link rel="stylesheet" href="${context}/css/tree.css?v=${now}">
<script src="${context}/vue/tree/treeView.vue?v=${now}"></script>
<script>
    treeView.api.getItems = '${treeBuilder}';
    treeView.api.taskEdit = '${taskEdit}';
    treeView.on = function(id){
        treeView.onClick(id)
    };
    subscriber.subscribe('${goalSubscribe}', treeView.handler);
</script>
<div id="treeView">
    <div>
        !{{title}}
    </div>
    <div class="tree-view">
        <tree-item :item="tree" :onclick="onClick"></tree-item>
    </div>
</div>