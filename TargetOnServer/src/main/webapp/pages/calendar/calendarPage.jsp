<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 06.07.2020
  Time: 14:24
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" href="${context}/css/datePicker.css">
<link rel="stylesheet" href="${context}/css/calendar/calendar.css">
<script type="application/javascript" src="${context}/vue/pathBuilder.vue"></script>
<script type="application/javascript" src="${context}/vue/weekNumber.vue"></script>
<script type="application/javascript" src="${context}/vue/templates/calendar.vue"></script>
<script type="application/javascript" src="${context}/vue/templates/datetime/datePicker.vue"></script>
<script type="application/javascript" src="${context}/vue/calendarPage.vue"></script>
<script>
  calendar.api.edit = '${edit}';
  calendar.api.remove = '${remove}';
  calendar.api.getCalendar = '${getCalendar}';
  calendar.getCalendarData();
  subscriber.subscribe('${taskSubscribe}', calendar.handler);
  subscriber.subscribe('${subscribe}', calendar.calendarHandler);
</script>
<div id="calendar" class="full-size">
  <div class="left-panel">
    <div class="panel-header">
      <div class="panel-header-content">
        <fmt:message key="calendar.today"/>
        <span class="text-button" style="float: right" v-on:click="edit()">
        <fmt:message key="button.add"/>
      </span>
      </div>
    </div>
    <div class="panel-content">
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
    </div>
  </div>
  <div class="central-panel">
    <div class="panel-header">
      <div class="panel-header-content">
        <span class="text-button" v-on:click="switchDate(-1)">
          <
        </span>
        <date-picker :date="date" :props="props"></date-picker>
        <span class="text-button" v-on:click="switchDate(1)">
          >
        </span>
        <span class="text-button" v-if="new Date(date).toLocaleDateString() !== new Date().toLocaleDateString()"
              v-on:click="resetDate()" style="position: absolute; right: 0">
          <fmt:message key="date.today"/>
        </span>
      </div>
    </div>
    <div class="panel-content">
      <div class="item-container">
        <div v-for="item in calendarBuilder()" :style="{'min-height': (item.length)+ 'pt'}"
             class="calendar-item" :class="{'filled' : item.title}">
          {{item}}
          <div v-if="item.title">
            {{item.title}}
          </div>
          <div style="font-size: 10px">
            <div v-for="(v, k) in item">
              {{k}}: {{v}}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="right-panel">
    <div class="panel-header">
      <div class="panel-header-content">
        <fmt:message key="calendar.active"/>
      </div>
    </div>
    <div class="panel-content">
      <div class="item-container">
        <div v-for="item in getItems()" v-on:click="edit(-1, {category:item.category})"
             style="border: solid gray 1pt; padding: 2px; margin: 2px">
          <div style="font-size: 8pt">
            {{getPath(item)}}
          </div>
          <div>
            {{item.title}}
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
