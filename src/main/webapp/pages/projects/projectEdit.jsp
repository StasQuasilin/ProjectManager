<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ua"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="${context}/css/projects/ProjectEdit.css">
<script src="${context}/js/projects/projectEdit.js"></script>
<script>
  edit.api.save = '${save}';
  edit.budgets.push({
    id:-1,
    name:'<fmt:message key="project.edit.no.budget"/>'
  });
  edit.budgets.push({
    id:-2,
    name:'<fmt:message key="project.edit.new.budget"/>'
  });
  <c:forEach items="${budgetTypes}" var="t">
  edit.budgetTypes.push({
    id:'${t}',
    name:'<fmt:message key="project.budget.${t}"/>'
  });
  </c:forEach>
  <c:forEach items="${budgets}" var="budget">
  edit.budgets.push({
    id:${budget.id},
    name:'${budget.name}'
  });
  </c:forEach>
  edit.project = {
    title:'',
    begin:new Date().toISOString().substring(0, 10),
    complete:new Date().toISOString().substring(0, 10),
    budget:{
      id:edit.budgets[0].id,
      sum:0,
      type:edit.budgetTypes[0].id
    }
  }
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
        <input id="title" style="width: 100%" v-model="project.title">
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
    <tr>
      <td align="right">
        <label for="complete">
          <fmt:message key="project.edit.date-complete"/>
        </label>
      </td>
      <td>
        <input id="complete" v-model="new Date(project.complete).toLocaleDateString()" class="date-input">
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
    <tr>
      <td>
        <label for="budget">
          <fmt:message key="project.budget"/>
        </label>
      </td>
      <td>
        <select id="budget" style="width: 100%" v-model="project.budget.id">
          <option v-for="budget in budgets" :value="budget.id">
            {{budget.name}}
          </option>
        </select>
      </td>
    </tr>
    <template v-if="project.budget.id == -2">
      <tr>
        <td>
          <label for="sum">
            <fmt:message key="project.budget.sum"/>
          </label>
        </td>
        <td>
          <input id="sum" type="number" step="0.01" v-model="project.budget.sum">
        </td>
      </tr>
      <tr v-for="bt in budgetTypes">
        <td colspan="2">
          <input type="radio" name="budgetSize" id="bt.id" :value="bt.id" v-model="project.budget.type">
          <label :for="bt.id">
            {{bt.name}}
          </label>
        </td>
      </tr>

    </template>
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
