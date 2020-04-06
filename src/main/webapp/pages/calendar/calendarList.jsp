<%--
  Created by IntelliJ IDEA.
  User: quasilin
  Date: 24.02.2019
  Time: 10:22
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<style>
  .moved{
    background-color: darkcyan;
  }
</style>
  <script src="${context}/vue/templates/calendarBox.vue"></script>
  <script src="${context}/vue/calendar/calendar.vue"></script>
  <link rel="stylesheet" href="${context}/css/calendar.css"/>
  <jsp:include page="../subscribePage.jsp"/>
  <script>
    calendar.api.getItems = '${getItems}';
    calendar.api.edit = '${edit}';
    calendar.api.editTime = '${editTime}';
    <c:forEach begin="1" end="7" var="i">
    calendar.days['${i}'] = '<fmt:message key="day.${i}"/> ';
    </c:forEach>
    subscribe(calendar);
  </script>
  <body>
  <div id="title-content" style="display: inline-block; background: transparent; margin: 0 4pt">
    <button onclick="loadModal('${edit}')">
      <fmt:message key="task.add"/>
    </button>
  </div>
    <table id="calendar" class="calendar" border="0" style="width: 100%; height: 100%">
      <tr>
        <td rowspan="2">
          <div class="calendar-item-container">
            <draggable :list="items" group="task">
              <div v-for="item in items" v-on:click="edit(item.id)" :key="item.id" >
                <calendar-item :item="item"></calendar-item>
              </div>
            </draggable>
          </div>
        </td>
        <td style="width: 50%">
          <div style="height: 100%">
            <div style="width: 100%; text-align: center; display: inline-block; background-color: orangered">
              <span v-on:click="switchDay(-1)">
                <
              </span>
              <span>
                {{days[new Date(date).getDay()]}}
              </span>
              <span style="padding: 0 4pt">
                {{new Date(date).toLocaleDateString()}}
              </span>
              <span v-on:click="switchDay(1)">
                >
              </span>
            </div>
          </div>
        </td>
      </tr>
      <tr>
        <td style="height: 100%">
          <draggable :list="calendarItems" group="task" @add="add" :move="move" @end="drop" style="height: 100%; background-color: gray; overflow-y: scroll; overflow-x: hidden">
<%--            <div style="height: 100%; background-color: gray; overflow-y: scroll; overflow-x: hidden">--%>
<%--              <draggable :list="calendarItems" group="task" @add="add" @remove="remove">--%>
<%--            <div v-for="index in 24" style="border: dotted 1pt; padding: 1pt; margin: 1pt">--%>
<%--              <draggable :list="calendarBuffer[index - 1]" group="task" @add="drop(calendarBuffer[index - 1], index - 1)" @end="drop(calendarBuffer[index - 1], index - 1)">--%>

                <div v-for="(item, index) in calendarItems" style="border: dotted 0.8pt" :class="{'moved' : item.move}">
                  <span>
                    {{item}}
                  </span>
<%--                  <calendar-item v-else :item="item"></calendar-item>--%>
                </div>
<%--              </draggable>--%>

<%--                <template v-if="item.empty" >--%>
<%--                  <div class="calendar-time" :class="{'calendar-time-current' : index == time}" v-on:click="edit(-1, index)">--%>
<%--                    {{index}}:00 {{item}}--%>
<%--                  </div>--%>
<%--                </template>--%>
<%--                <calendar-item v-else :item="item.task"></calendar-item>--%>
<%--            </div>--%>
          </draggable>
<%--          </div>--%>
        </td>
      </tr>
    </table>
  </body>
</html>
