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
        tree.api.taskSettings = '${setting}';
        tree.deafaultTaskName = '<fmt:message key="default.task.name"/>';
        tree.props = {
            onSelect:function(item){
                tree.select(item);
            }
        };
        subscribe(tree);
    </script>
    <table id="tree" class="tree-page">
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
                <div class="tree">
                    <tree-view :item="tree" :props="props"></tree-view>
                </div>
            </td>
        </tr>
        <tr>
            <td style="height: 100%; vertical-align: top">
                <div style="max-height: 100%; height: 100%; overflow-y: scroll; background-color: teal">
                    <div v-for="item in items" class="tree-list-item">
                        <span v-on:click="select(item)"
                              style="display: inline-block;width: calc(100% - 28pt)">
                            <span v-if="item.isGroup">
                                -
                            </span>
                            <span v-else>
                                &nbsp;
                            </span>
                            <span>
                                {{item.title}}
                            </span>
                        </span>
                        <span style="float: right" class="tree-list-settings" >
                            <span v-on:click="remove(item)" title="Remove">
                                <img style="width: 8pt;" src="${context}/img/buttons/trash.svg" alt="">
                            </span>
                            <span v-on:click="settings(item)" title="Settings">
                                <img style="width: 12pt;" src="${context}/img/buttons/settings.svg" alt="">
                            </span>
                        </span>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</html>
