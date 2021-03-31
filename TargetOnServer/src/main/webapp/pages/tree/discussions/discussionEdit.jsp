<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 2021-03-31
  Time: 16:02
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<script src="${context}/vue/editor.vue"></script>
<script src="${context}/vue/simpleEditor.vue"></script>
<script>
    editor.api.save = '${save}';
    editor.item = {
        id:-1,
        title:'Olololo',
        text:'sfsdfsdfsdfsdf'
    }
</script>
<div id="editor">
    <div>
        {{item.title}}
    </div>
    <textarea>{{item.text}}</textarea>
</div>
<div class="modal-buttons">
    <button onclick="closeModal()">
        &timesbar;
    </button>
    <button v-on:click="save()">

    </button>
</div>
</html>
