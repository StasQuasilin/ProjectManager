<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ua"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="${context}/js/tree/taskEdit.js"></script>
<script>
  edit.api.save = '${save}';
  edit.task = {
    title:'',
    date:new Date().toISOString().substring(0, 10),
    parent:${parent}
  }
</script>
<div id="editor">
  <table>
    <tr>
      <td colspan="2">
        <label for="title">
          <fmt:message key="task.title"/>*
        </label>
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <input id="title" style="width: 100%" v-model="task.title" autocomplete="off">
      </td>
    </tr>
    <tr>
      <td>
        <label for="date">
          <fmt:message key="task.date"/>
        </label>
      </td>
      <td>
        <input id="date" style="width: 8em" v-model="new Date(task.date).toLocaleDateString()">
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <button onclick="closeModal()">
          <fmt:message key="buttons.cancel"/>
        </button>
        <button v-on:click="save">
          <fmt:message key="buttons.save"/>
        </button>
      </td>
    </tr>
  </table>
</div>

</html>
