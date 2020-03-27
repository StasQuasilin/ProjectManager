<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/tree/TreeList.css">
<script src="${context}/vue/templates/tree/tree.vue"></script>
<script>
    Vue.component('tree-view', treeView);
</script>
<script src="${context}/js/tree/TreeList.js"></script>
    <jsp:include page="../subscribePage.jsp"/>
    <script>
        tree.api.edit = '${edit}';
        tree.api.getSubs = '${getSubTasks}';
        tree.deafaultTaskName = '<fmt:message key="default.task.name"/>';
        tree.props = {
            onSelect:function(item){
                tree.select(item);
            }
        };
        subscribe(tree);
    </script>
    <table id="tree" class="tree">
        <tr>
            <td style="width: 50%">
                <template v-if="selected">
                    <span v-for="p in selected.path">
                        <a>
                            {{p.title}}
                        </a>/
                    </span>
                    <span>
                        {{selected.title}}
                    </span>
                </template>
                /<span>..</span>
                <div style="display: inline-block" v-if="selected" v-on:click="newTask()">
                    +<fmt:message key="tree.add.task"/>
                </div>
            </td>
            <td rowspan="2" style="background-color: cadetblue; vertical-align: top">
                <tree-view :item="tree" :props="props"></tree-view>
            </td>
        </tr>
        <tr>
            <td style="height: 100%; vertical-align: top">
                <div style="max-height: 100%; height: 100%; overflow-y: scroll; background-color: teal">
                    <div v-on:click="select(item)" v-for="item in items" style="background-color: orange; padding: 2pt 4pt; margin: 2pt">
                        <span v-if="item.isGroup">
                            -
                        </span>
                        <span v-else>
                            &nbsp;
                        </span>
                        <span>
                            {{item.title}}
                        </span>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</html>
