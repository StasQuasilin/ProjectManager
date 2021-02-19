<script src="${context}/vue/tree/treeItem.vue"></script>
<script>
    Vue.component('tree-item', treeItem);
</script>
<link rel="stylesheet" href="${context}/css/tree.css">
<script src="${context}/vue/tree/treeView.vue"></script>
<script>
    treeView.api.getItems = '${treeBuilder}',
    subscriber.subscribe('${goalSubscribe}', treeView.handler);
</script>
<div id="treeView" class="tree-view">
    <tree-item :item="tree"></tree-item>
</div>