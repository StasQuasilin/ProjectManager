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
<script src="${context}/vue/pathBuilder.vue"></script>
<script src="${context}/vue/weekNumber.vue"></script>
<script src="${context}/vue/calendarPage.vue"></script>
<script>
  calendar.api.edit = '${edit}';
  calendar.api.getCalendar = '${getCalendar}';
  calendar.getCalendar();
  subscriber.subscribe('${subscribe}', calendar.handler);
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
      <span class="text-button" v:on:click="switchDate(-1)">
        <
      </span>
      <span v-if="scale === 'day'">
        Day
        {{new Date(date).toLocaleDateString()}}
      </span>
      <span v-else-if="scale === 'week'">
        Week
        {{getWeekNumber(new Date(date))}}
      </span>
      <span v-else-if="scale === 'month'">
        Month
        {{new Date(date).getMonth() + 1}}
      </span>
      <span v-else>
        Year
        {{new Date(date).getFullYear()}}
      </span>
      <span class="text-button" v:on:click="switchDate(1)">
        >
      </span>

<%--      <span style="float: right">--%>
<%--        <span v-for="s in scales" v-on:click="scale = s" class="text-button">--%>
<%--          [ {{s}} ]--%>
<%--        </span>--%>
<%--      </span>--%>
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
        <div v-for="item in getCalendarItems()">
          {{item.time}} {{item.title}}
        </div>
      </div>
    </td>
    <td style="width: 25%; height: 100%">
      <div class="item-container">
        <div v-for="item in getItems()" style="border: solid gray 1pt; padding: 2px; margin: 2px">
          <div style="font-size: 10pt">
            <span>
              {{getPath(item)}}
            </span>
          </div>
          <div>
            {{item.title}}
          </div>
        </div>
      </div>
    </td>

  </tr>
</table>
