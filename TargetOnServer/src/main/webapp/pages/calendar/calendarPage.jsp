<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 06.07.2020
  Time: 14:24
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${locale}"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" href="${context}/css/calendar/calendar.css">
<script src="${context}/vue/pathBuilder.vue"></script>
<script src="${context}/vue/weekNumber.vue"></script>
<script src="${context}/vue/calendarPage.vue"></script>
<script>
  calendar.api.edit = '${edit}';
  calendar.api.remove = '${remove}';
  calendar.api.getCalendar = '${getCalendar}';
  calendar.getCalendarData();
  subscriber.subscribe('${taskSubscribe}', calendar.handler);
  subscriber.subscribe('${subscribe}', calendar.calendarHandler);
</script>
<table id="calendar" class="full-size" border="1">
  <tr>
    <td style="text-align: center">
      <fmt:message key="calendar.today"/>
      <span class="text-button" style="float: right" v-on:click="edit()">
        <fmt:message key="button.add"/>
      </span>
    </td>
    <td style="text-align: center">
      <span class="text-button" v-on:click="switchDate(-1)">
        &#9664;
      </span>
      <span class="text-button">
        {{new Date(date).toLocaleDateString()}}
      </span>
      <span class="text-button" v-if="new Date(date) !== new Date()" v-on:click="resetDate()">
        &#128198;
      </span>
      <span class="text-button" v-on:click="switchDate(1)">
        &#9654;
      </span>
    </td>
    <td style="text-align: center">
      <fmt:message key="calendar.active"/>
    </td>
  </tr>
  <tr>
    <td style="width: 25%">
      <div class="item-container">
        <div v-for="item in getOtherItems()" v-on:click="edit(item.id)">
          <div>
            <span v-if="item.date">
              {{item.date}}
            </span>
            <span>
              {{item.title}}
            </span>
          </div>
        </div>
      </div>
    </td>
    <td style="width: 50%">
      <div class="item-container">
        <div v-for="item in calendarBuilder()" :style="{'min-height': (item.length)+ 'pt'}"
             class="calendar-item" :class="{'filled' : item.title}">
              <template v-for="e in item.events" style="display: inline-block">
                <template v-if="e">
                  {{e}}
                </template>
                <template v-else>
                  -
                </template>
              </template>
<%--          <div class="calendar-item-time">--%>
<%--            {{item.from.toLocaleTimeString().substring(0, 5)}} - {{item.to.toLocaleTimeString().substring(0, 5)}}--%>
<%--            <div class="calendar-item-menu">--%>
<%--              <span class="text-button" v-on:click="edit(item.id)">--%>
<%--                /--%>
<%--              </span>--%>
<%--              <span class="text-button" v-on:click="remove(item.id)">--%>
<%--                &times;--%>
<%--              </span>--%>
<%--            </div>--%>
<%--          </div>--%>
<%--          <div v-if="item.title">--%>
<%--            {{item.title}}--%>
<%--          </div>--%>
          <div style="font-size: 10px">
            <div v-for="(v, k) in item">
              {{k}}: {{v}}
            </div>
          </div>
        </div>
      </div>
    </td>
    <td style="width: 25%; height: 100%">
      <div class="item-container">
        <div v-for="item in getItems()" v-on:click="edit(-1, {category:item.category})" style="border: solid gray 1pt; padding: 2px; margin: 2px">
          <div style="font-size: 8pt">
            {{getPath(item)}}
          </div>
          <div>
            {{item.title}}
          </div>
        </div>
      </div>
    </td>
  </tr>
</table>
