<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
  <link rel="stylesheet" href="${context}/css/ProjectPage.css">
  <link rel="stylesheet" href="${context}/ProjectBox.css">
  <script src="${context}/vue/templates/projectBox.vue"></script>
  <script src="${context}/vue/projects/projectList.vue"></script>
  <jsp:include page="../subscribePage.jsp"/>
  <script>
    list.api.edit = '${edit}';
    list.api.remove = '${delete}';
    list.props = {
      titles:{
        dateBegin:'<fmt:message key="project.edit.date-begin"/>',
        dateComplete:'<fmt:message key="project.edit.date-complete"/>',
        todo:'<fmt:message key="project.tasks.active"/>',
        progressing:'<fmt:message key="project.tasks.progressing"/>',
        done:'<fmt:message key="project.tasks.done"/>'
      },
      onEdit:function(id){
        list.edit(id);
      },
      onDelete:function(id){
        list.removeProject(id);
      }
    };
    subscribe(list);
  </script>


  <div id="title-content" style="display: inline-block; background: transparent; margin: 0 4pt">
    <button onclick="loadModal('${edit}')">
      <fmt:message key="project.new"/>
    </button>
  </div>
  <div id="list">
    <div class="project-container">
      <div v-for="(value) in byRole('owner')" :key="value.id" >
        <item-view :project="value" :props="props" class="project-box"></item-view>
      </div>
    </div>
  </div>
</html>
