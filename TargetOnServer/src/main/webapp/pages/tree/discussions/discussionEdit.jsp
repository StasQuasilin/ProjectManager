
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 2021-03-31
  Time: 16:02
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/editor.vue"></script>
<script src="${context}/vue/simpleEditor.vue"></script>
<script>
    editor.api.save = '${save}';
    editor.item = {
        id:-1,
        task:${task.id},
        title:'${task.title}',
        text:''
    };
    <c:if test="${not empty parent}">
    editor.item.parent = ${parent.id};
    </c:if>
</script>
<div id="editor">
    <div>
        <label for="text">
            {{item.title}}
        </label>
    </div>
    <textarea id="text" style="width:100%; min-width: 400px; height: 250px; resize: none"
              ondblclick="this.select()" v-model="item.text">{{item.text}}</textarea>
    <div class="modal-buttons">
        <button onclick="closeModal()">
            &timesbar;
        </button>
        <button v-on:click="save">
            <fmt:message key="button.save"/>
        </button>
    </div>
</div>
</html>
