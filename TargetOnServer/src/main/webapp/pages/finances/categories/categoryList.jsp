<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 29.09.20
  Time: 15:07
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>

    <script type="text/javascript" src="${context}/vue/finances/categoryTreeTemplate.vue"></script>
    <script type="text/javascript" src="${context}/vue/finances/categoryList.vue"></script>
    <script type="text/javascript">
        Vue.component(
            'category-tree-item',treeItem
        );
        categoryList.api.getCategories = '${getCategories}';
        categoryList.getCategories();
    </script>
    <span id="titleContent">
        +Add Category
    </span>
    <div id="categoryList">
        <category-tree-item :item="item" :key="item.id" v-for="item in categories"></category-tree-item>
    </div>
</html>
