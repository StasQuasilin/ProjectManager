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
        <div v-for="item in getCalendarItems()" v-on:click="edit(item.id)">
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
