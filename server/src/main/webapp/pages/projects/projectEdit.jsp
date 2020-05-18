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
edit.years[1] ='<fmt:message key="1.year"/>';
edit.years[2] = '<fmt:message key="2.years"/>';
edit.years[3] = '<fmt:message key="many.years"/>';
edit.months[1] = '<fmt:message key="1.month"/>';
edit.months[2] = '<fmt:message key="2.months"/>';
edit.months[3] = '<fmt:message key="many.months"/>';
edit.addMonth(1);
</script>
  <div id="editor" class="edit">
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
        <td>
          <label for="usePeriod">
            <span v-if="withPeriod">
              <fmt:message key="project.date.use"/>
            </span>
            <span v-else>
              <fmt:message key="project.date.without"/>
            </span>
          </label>
        </td>
        <td>
          <input id="usePeriod" type="checkbox" v-model="withPeriod">
        </td>
      </tr>
      <template v-if="withPeriod">
        <tr>
          <td align="right">
            <label for="begin">
              <fmt:message key="project.edit.date-begin"/>
            </label>
          </td>
          <td>
            <input id="begin" v-model="project.begin" class="date-input" >
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
              <input id="complete" v-model="project.end" class="date-input">
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <fmt:message key="project.length"/>:
              <span v-if="projectLength.years > 0">
              {{projectLength.years.toLocaleString()}}
              <span v-if="projectLength.years == 1">
                {{years[1].toLowerCase()}}
              </span>
              <span v-else-if="projectLength.years >= 2 && projectLength.years <= 4">
                {{years[2].toLowerCase()}}
              </span>
              <span v-else>
                {{years[3].toLowerCase()}}
              </span>
            </span>
              <span v-if="projectLength.month > 0">
              {{projectLength.month.toLocaleString()}}
              <span v-if="projectLength.month == 1">
                {{months[1].toLowerCase()}}
              </span>
              <span v-else-if="projectLength.month >= 2 && projectLength.month <= 4">
                {{months[2].toLowerCase()}}
              </span>
              <span v-else>
                {{months[3].toLowerCase()}}
              </span>
            </span>
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
      </template>
      <tr v-if="!withCost">
        <td colspan="2">
          <span v-on:click="withCost = true">
            <fmt:message key="project.cost.add"/>
          </span>
        </td>
      </tr>
      <tr v-else>
        <td>
          <label for="cost">
            <fmt:message key="project.cost"/>
          </label>
        </td>
        <td>
          <input id="cost" v-model.number="project.cost" style="width: 100px">
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
