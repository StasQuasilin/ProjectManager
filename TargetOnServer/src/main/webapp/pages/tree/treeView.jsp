<script src="${context}/vue/tree/treeItem.vue?v=${now}"></script>
<script>
    Vue.component('tree-item', treeItem);
</script>
<link rel="stylesheet" href="${context}/css/tree.css?v=${now}">
<script src="${context}/vue/tree/treeView.vue?v=${now}"></script>
<script>
    treeView.api.getItems = '${treeBuilder}';
    treeView.api.taskEdit = '${taskEdit}';
    treeView.props.edit = '${taskEdit}';
    treeView.props.delete = '${taskDelete}';
    treeView.on = function(id){
        treeView.onClick(id)
    };
    subscriber.subscribe('${treeSubscribe}', treeView.handler);
</script>
<div id="treeView" style="padding: 8pt">
    <div class="tree-view">
        <tree-item :item="tree" :onclick="onClick" :props="props"></tree-item>
    </div>
</div>