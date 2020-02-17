<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ua"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="${context}/vue/projects/projectList.js"></script>
<script>
  projectList.api.update = '${update}';
  projectList.update();
</script>
<div id="title-content" style="display: inline-block; background: transparent; margin: 0 4pt">
  <button onclick="loadModal('${edit}')">
    <fmt:message key="budget.new"/>
  </button>
</div>

<div id="list">
  <div v-for="(value, key) in items">
    <span v-if="value.type == 'project'">
    </span>
    {{value}}
    {{value.title}}
    {{value.sum}} {{value.currency}}
  </div>
</div>
</html>
