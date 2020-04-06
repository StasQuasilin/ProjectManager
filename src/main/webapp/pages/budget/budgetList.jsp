<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
  var projectBox = {}
</script>
<script src="${context}/vue/projects/projectList.vue"></script>
<jsp:include page="../subscribePage.jsp"/>
<script>
  list.api.edit = '${edit}';
  subscribe(list);
</script>

<div id="title-content" style="display: inline-block; background: transparent; margin: 0 4pt">
  <button onclick="loadModal('${edit}')">
    <fmt:message key="budget.new"/>
  </button>
</div>

<div id="list">
  <div v-for="(value, key) in items" v-on:click="edit(value.id)">
    {{value.title}}
    {{value.amount}} {{value.currency}}
  </div>
</div>
</html>
