<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/projects/ProjectEdit.css">
<script src="${context}/js/projects/projectEdit.vue"></script>
<script>
edit.api.save = '${save}';
<c:if test="${not empty project}">
edit.project = ${project.toJson()};
</c:if>
</script>
  <div id="editor">
    <table>
      <tr>
        <td colspan="2">
          <label for="title">
            <fmt:message key="project.edit.title"/>
          </label>
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <input id="title" style="width: 100%" v-model="project.title" autocomplete="off">
        </td>
      </tr>
      <tr>
        <td align="right">
          <label for="begin">
            <fmt:message key="project.edit.date-begin"/>
          </label>
        </td>
        <td>
          <input id="begin" v-model="new Date(project.begin).toLocaleDateString()" class="date-input" >
        </td>
      </tr>
      <template v-if="useEndDate">
        <tr>
          <td align="right">
            <label for="complete">
              <fmt:message key="project.edit.date-complete"/>
            </label>
          </td>
          <td>
            <input id="complete" v-model="new Date(project.end).toLocaleDateString()" class="date-input">
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <fmt:message key="project.length"/>:
            {{projectLength}}
          </td>
        </tr>
        <tr>
          <td colspan="2" style="font-size: 8pt">
            <div style="display: inline-block">
              <div>
                <a v-on:click="addMonth(1)">
                <span>
                  +<fmt:message key="project.edit.date.month"/>
                </span>
                </a>
              </div>
              <div>
                <a v-on:click="addMonth(-1)">
                <span>
                  -<fmt:message key="project.edit.date.month"/>
                </span>
                </a>
              </div>
            </div>
            <div style="display: inline-block">
              <div>
                <a v-on:click="addMonth(3)">
                <span>
                  +<fmt:message key="project.edit.date.3month"/>
                </span>
                </a>
              </div>
              <div>
                <a v-on:click="addMonth(-3)">
                <span>
                  -<fmt:message key="project.edit.date.3month"/>
                </span>
                </a>
              </div>
            </div>
            <div style="display: inline-block">
              <div>
                <a v-on:click="addMonth(6)">
                <span>
                  +<fmt:message key="project.edit.date.6month"/>
                </span>
                </a>
              </div>
              <div>
                <a v-on:click="addMonth(-6)">
                <span>
                  -<fmt:message key="project.edit.date.6month"/>
                </span>
                </a>
              </div>
            </div>
            <div style="display: inline-block">
              <div>
                <a v-on:click="addMonth(12)">
                <span>
                  +<fmt:message key="project.edit.date.1year"/>
                </span>
                </a>
              </div>
              <div>
                <a v-on:click="addMonth(-12)">
                <span>
                  -<fmt:message key="project.edit.date.1year"/>
                </span>
                </a>
              </div>
            </div>
          </td>
        </tr>
      </template>
      <tr v-else>
        <td colspan="2" align="right">
          <span v-on:click="useEndDate=true"><fmt:message key="add.project.end"/></span>
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
